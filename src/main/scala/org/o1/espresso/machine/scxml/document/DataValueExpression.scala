package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement
/**
  * Created by ricardo on 12/20/16.
  */
trait DataValueExpression extends DocumentElement {
  lazy val value: Option[String]=None
  val valuexpr:Option[String] = None
}
