package org.o1.espresso.machine.scxml.document

import java.net.URI

/**
  * Created by ricardo on 12/20/16.
  */
sealed trait DatamodelData {
  val id:String
  val model:String = "null"
}
case class Src(override val id: String, val src:URI, override val model:String) extends DatamodelData
case class Expr(override val id: String, val expr:ExpressionValue, override val model:String) extends DatamodelData
