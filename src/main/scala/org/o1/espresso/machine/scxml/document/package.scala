package org.o1.espresso.machine.scxml

/**
  * Created by ricardo on 12/21/16.
  */
package object document {
  //to be used on elements which contains "expression" like attributes
  sealed trait ExprStringValue { def toString: String }
  case class ExpressionValue(override val toString:String) extends ExprStringValue
  case class StringValue(override val toString:String) extends ExprStringValue

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

  object IoType extends Enumeration {
    val SCXML:Value = Value("http://www.w3.org/TR/scxml/#SCXMLEventProcessor")
  }
}
