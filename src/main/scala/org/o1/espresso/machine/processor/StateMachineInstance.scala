package org.o1.espresso.machine.processor

import org.o1.espresso.machine.StateMachineProcess
import org.o1.espresso.machine.dsl.StateMachine

trait StateMachineInstance extends StateMachineProcess with StateMachine {
  lazy val instanceId:String = name.getOrElse("anon") + "." + descriptor.processId
}
