package org.o1.espresso.machine.scxml

import org.o1.espresso.machine.StateMachineProcess

/**
  * Created by ricardo on 12/20/16.
  */
trait DocumentElementProcessor[E <: DocumentElement,P <: StateMachineProcess] {
  def process(element:E,id:Option[String]=None):P
}
