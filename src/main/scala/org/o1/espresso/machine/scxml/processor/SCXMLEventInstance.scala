package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessDescriptor, ProcessStatus, StateMachineProcess}

/**
  * Created by ricardo on 1/29/17.
  */
class SCXMLEventInstance(val event:Event, val processId:Long, val parentProcessId:Long) extends StateMachineProcess {
  override val descriptor: ProcessDescriptor = ProcessDescriptor(
    processId,
    parentProcessId,
    ProcessStatus.Active)

}
