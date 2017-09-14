package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}
import org.o1.espresso.machine.scxml.document.{SCXML}

import scala.collection.mutable


trait SCXMLInstance extends StateMachineProcess { this: SCXML =>

  lazy val instanceId:String = name.getOrElse("anon") + "." + descriptor.processId
  protected[this] val listeners = new mutable.MutableList[SCXMLEventListener]
  protected[this] val configuration = new mutable.MutableList[SCXMLState]
  protected[this] val processStatusHistory = new mutable.MutableList[ProcessDescriptor]

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
    override val descriptor = (processStatusHistory += ProcessDescriptor(processId,-1L,ProcessStatus.Idle)).last

    override def localName= scxml.localName
    override lazy val name = scxml.name
    override def version = scxml.version
    override def binding = scxml.binding
    override def datamodel = scxml.datamodel
    override def script = scxml.script
    override def states = scxml.states
    override def finals = scxml.finals

  }
}