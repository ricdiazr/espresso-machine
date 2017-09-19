package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML}

import scala.collection.mutable


trait SCXMLInstance extends StateMachineProcess { this: SCXML =>

  lazy val instanceId:String = name.getOrElse("anon") + "." + descriptor.processId
  protected[this] val listeners = new mutable.MutableList[SCXMLEventListener]
  protected[this] val configuration = new mutable.MutableList[SCXMLState]
  protected[this] val processStatusHistory:mutable.ArrayBuffer[ProcessDescriptor]

  override protected[this] def signal(status:ProcessStatus.Value): StateMachineProcess = {
    if(status == descriptor.status)
      return SCXMLInstance.this
    processStatusHistory+=ProcessDescriptor(descriptor.processId,descriptor.parentProcessId,status)
    SCXMLInstance.this
  }
  def activeState:Option[SCXMLState] = {
    if(descriptor.status == ProcessStatus.Active)
      Some(configuration.last)
    else None
  }

  protected[processor] def activate(p:(SCXMLSession) => SCXMLProcessingStep): SCXMLProcessingStep = {
    if(descriptor.status != ProcessStatus.Idle)
      new IllegalStateException("state machine is already active")

    p(signal(ProcessStatus.Active).asInstanceOf[SCXMLSession])
  }

}

object SCXMLInstance {
  def apply(scxml: SCXML, processId:Long) = new SCXMLInstance
    with StateMachineProcess with SCXML {
    override protected[this] val processStatusHistory:mutable.ArrayBuffer[ProcessDescriptor]
    = mutable.ArrayBuffer[ProcessDescriptor](ProcessDescriptor(processId,-1L,ProcessStatus.Idle))
    override def descriptor =processStatusHistory.last
    override lazy val name = scxml.name
    lazy val ln= scxml.localName
    lazy val ver = scxml.version
    lazy val bind = scxml.binding
    lazy val dm:Datamodel = scxml.datamodel
    lazy val scpt = scxml.script
    lazy val sS = scxml.states
    lazy val fS = scxml.finals

    override def localName= ln

    override def version = ver
    override def binding = bind
    override def datamodel:Datamodel = dm
    override def script = scpt
    override def states = sS
    override def finals = fS

  }
}