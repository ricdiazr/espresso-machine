package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}
import org.o1.espresso.machine.scxml._
import org.o1.espresso.machine.scxml.document.{Datamodel, SCXML}

import scala.collection.mutable


trait SCXMLInstance extends StateMachineProcess {
  this: SCXML =>
  
  val configuration = new mutable.MutableList[SCXMLState]
  val intQueue:EventQueue
  val extQueue:EventQueue
  val instanceId:String = name + "." + descriptor.processId
  val listeners = new mutable.MutableList[SCXMLEventListener]

  def activeState:Option[SCXMLState] = {
    if(descriptor.status == ProcessStatus.Active)
      Some(configuration.last)
    else None
  }
}

object SCXMLInstance {
  def apply(scxml: SCXML, internal:EventQueue, external:EventQueue, processId:Long) = new SCXMLInstance with SCXML {
    val descriptor = ProcessDescriptor(processId,-1L,ProcessStatus.Idle)
    override val intQueue = internal
    override val extQueue = external

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