package org.o1.espresso.machine.scxml

/**
  * Created by ricardo on 12/21/16.
  */
package object document {
  object StateType extends Enumeration {
    val Initial, Parallel, Compound, Atomic = Value
  }

  object ExecutableOn extends Enumeration {
    val Entry, Exit, Ordered = Value
  }

  object TransitionType extends Enumeration {
    val Internal, External = Value
  }

  object HistoryType extends Enumeration {
    val Shallow, Deep = Value
  }

}
