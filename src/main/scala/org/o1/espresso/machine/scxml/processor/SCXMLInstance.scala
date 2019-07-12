package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML, State}

import scala.collection.mutable


trait SCXMLInstance extends StateMachineProcess with SCXML {

  lazy val instanceId:String = name.getOrElse("anon") + "." + descriptor.processId

  protected[this] val processStatusHistory:mutable.ArrayBuffer[ProcessDescriptor]

  override protected[this] def signal(status:ProcessStatus.Value): StateMachineProcess = {
    if(status == descriptor.status)
      return SCXMLInstance.this
    processStatusHistory+=ProcessDescriptor(descriptor.processId,descriptor.parentProcessId,status)
    SCXMLInstance.this
  }

  protected[processor] def activate = {
    if(descriptor.status != ProcessStatus.Idle)
      new IllegalStateException("state machine is already active")
    signal(ProcessStatus.Active)
  }

}

object SCXMLInstance {
  def apply(scxml: SCXML, processId:Long) = new SCXMLInstance {
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