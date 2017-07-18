package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.DataValueExpression

import scala.xml.Node

/**
  * Created by ricardo on 7/17/17.
  */
trait DataValueExpressionElement extends DataValueExpression {
}
object DataValueExpression {
    def apply(node: Node,valueAttr:String, exsuffix:String =  "expr") = new DataValueExpression {
      override lazy val value = if( !(node \@ valueAttr).isEmpty ) Some(node \@ valueAttr) else valuexpr
      override val valuexpr = if( (node \@ valueAttr).isEmpty ) Some(node \@ valueAttr + exsuffix) else None
      override def localName = node.text
    }
  }

