package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.ProcessStatus
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML, State}
import org.o1.espresso.machine.scxml.{DocumentElement, DocumentElementProcessor}
import org.o1.logging.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait SCXMLElementProcessor
  extends DocumentElementProcessor[SCXML,SCXMLSession] with Logging {

  val thisProcessor = this
  val registry:SCXMLInstanceRegistry
  val interpreter:SCXMLElementInterpreter

  override def process(scxml: SCXML): SCXMLSession = {
    debug(s"process ${scxml.name}")
    interpret(scxml, registry.last match {
      case Some(p) =>p.descriptor.processId +1
      case _=> 1
    })
  }

  def interpret(scxml: SCXML, id:Long): SCXMLSession = {
    val session:SCXMLSession = registry.register(SCXMLInstance(scxml, id)).last match {
      case Some(s) => s.asInstanceOf[SCXMLSession]
    }
    startup(session)
    session
  }

  protected[processor] def eventQueue(isExternal:Boolean = false):EventQueue


  /**
    * At startup, the SCXML Processor MUST place the state machine in the configuration specified
    * by the 'initial' attribute of the <scxml> element.
    * @param scxml Session registered at this processor's registry
    * @return
    */
  def startup(scxml:SCXMLSession):SCXMLProcessingStep = scxml.activate(SCXMLMacroStep(_,thisProcessor))

  protected[processor] def signal(target:SCXMLSession,status:ProcessStatus.Value,q:EventQueue):Unit = {
    q.enqueue(SignalEvent(target,status))
  }

}
protected object SCXMLMacroStep {

  def apply(scxml:SCXMLSession, processor:SCXMLElementProcessor): SCXMLProcessingStep
  = new SCXMLProcessingStep {
    val internalQueue: EventQueue = processor.eventQueue()
    val externalQueue: EventQueue = processor.eventQueue(true)
    val datamodel:SCXMLDatamodel[AnyRef] = processor.interpreter.datamodel(scxml.binding,scxml.datamodel)
    override def isRunning = scxml.descriptor.status != ProcessStatus.Idle && !isDone

    override def isDone = scxml.descriptor.status == ProcessStatus.Halt

  }
}