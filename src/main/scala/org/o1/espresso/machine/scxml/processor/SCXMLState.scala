package org.o1.espresso.machine.scxml
package processor

import org.o1.espresso.machine.scxml.document.{SCXML, State, Transition}
import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}

import scala.collection.mutable

/**
  * Created by ricardo on 12/20/16.
  */
trait SCXMLState extends StateMachineProcess
  with mutable.Publisher[Event] {
  val configuration:SCXMLStateConfiguration[State]
  def datamodel[_]:SCXMLDatamodel[_]
}

object SCXMLState {
  def apply(state:State, processId:Long, parent:SCXMLState, dmopt:Option[SCXMLDatamodel[_]]): SCXMLState = new SCXMLState {
    override val configuration =
      new SCXMLStateConfiguration[State](Some(parent.configuration), parent.configuration.instanceId, state)
    override def descriptor = ProcessDescriptor(processId, parent.descriptor.processId,parent.descriptor.status)
    override def datamodel[_]:SCXMLDatamodel[_] = dmopt match {
      case Some(d) => parent.datamodel.merge(d)
      case None => parent.datamodel
    }
  }

  def apply(scxml:SCXMLInstance, state:State, dm:SCXMLDatamodel[_]): SCXMLState = new SCXMLState {
    override val configuration =
      new SCXMLStateConfiguration[State](None, scxml.instanceId, state)
    override def descriptor = scxml.descriptor
    override def datamodel[_]:SCXMLDatamodel[_] = dm[_]
  }
}
