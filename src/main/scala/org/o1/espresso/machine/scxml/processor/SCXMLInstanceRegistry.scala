package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.StateMachineProcessRegistry
import org.o1.logging.Logging

trait SCXMLInstanceRegistry extends StateMachineProcessRegistry[SCXMLInstance] with Logging {

  def apply(instanceId:String): Option[SCXMLInstance] = {
    debug(s"getting SCXMLInstance(${instanceId})")
    processes.find((t) => t._2.instanceId == instanceId) match {
      case Some((t)) => Some(t._2)
    }
  }
}