package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.ProcessStatus
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML, State}
import org.o1.espresso.machine.scxml.{DocumentElement, DocumentElementProcessor}
import org.o1.logging.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait SCXMLElementProcessor
  extends DocumentElementProcessor[SCXML,SCXMLInstance] with Logging {

  val thisProcessor = this
  val registry:SCXMLInstanceRegistry
  val binder:DataBinding

  def process(scxml: SCXML, id: Option[String] = None): SCXMLInstance = {
    debug(s"process ${scxml.name} - ${id}")
    val scxmlInstance:SCXMLInstance = registry.register(
      SCXMLInstance(scxml,
        eventQueue(false),
        eventQueue(true),
        registry.last.descriptor.processId+1)).last
    execute(scxmlInstance).onComplete {
      case Success(ps) => println("done")
      case Failure(ex) => println("oops something went wrong")
    }
    scxmlInstance
  }

  def eventQueue(isExternal:Boolean):EventQueue

  def execute(scxml:SCXMLInstance):Future[SCXMLProcessingStep] = Future {
    new SCXMLProcessingStep {
      val (parent,children) = interpret(scxml.asInstanceOf[SCXML])
      override def isDone = scxml.descriptor.status == ProcessStatus.Halt
    }
  }

  def interpret[E <: DocumentElement](e:E) = {
    e match {
      case e: SCXML => if (isDocumentValid(e)) (e, e.datamodel)
      else throw NonConformantSCXMLException(e.localName, "does not define a least one valid state")
    }
  }

  def isDocumentValid(scxml:SCXML):Boolean = {
    if (scxml.states.size >= 1) true
    else if (scxml.finals.size >= 1 ) true
    else scxml.initial match {
      case Some(i) => scxml.states.count((s:State)=> s.id == i) == 1
      case _ => false
    }
  }
}