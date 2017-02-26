package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.StateMachineProcessRegistry
import org.o1.logging.Logging

trait SCXMLInstanceRegistry extends StateMachineProcessRegistry[SCXMLInstance] with Logging {

  def get(instanceId:String): Option[SCXMLInstance] = {
    debug(s"getting SCXMLInstance(${instanceId})")
    processes.filter(_.instanceId == instanceId).headOption
  }
}