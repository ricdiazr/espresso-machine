package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.ProcessStatus
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML}
import org.o1.espresso.machine.scxml.{DocumentElement, DocumentElementProcessor}
import org.o1.logging.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait SCXMLElementProcessor
  extends DocumentElementProcessor[SCXML,SCXMLInstance] with Logging {
  this: EventQueue with ExternalIO =>
  val thisProcessor = this
  val interpreter:SCXMLElementInterpreter
  val registry:SCXMLInstanceRegistry
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


  def databind(datamodel:Datamodel): DataBinding = new NullDataBinder

  def eventQueue(isExternal:Boolean):EventQueue

  def execute(scxml:SCXMLInstance):Future[SCXMLProcessingStep] = Future {
    new SCXMLProcessingStep {
      val (parent,children) = interpreter.interpret(scxml.asInstanceOf[SCXML])
      override def isDone = scxml.descriptor.status == ProcessStatus.Halt
    }
  }
}