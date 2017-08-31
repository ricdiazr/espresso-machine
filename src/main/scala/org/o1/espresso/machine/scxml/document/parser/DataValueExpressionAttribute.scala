package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{DataValueExpression, ExpressionValue, StringValue}

import scala.xml.Node

/**
  * Created by ricardo on 7/17/17.
  */
trait DataValueExpressionAttribute extends DataValueExpression {
}
object DataValueExpressionAttribute {

    def apply(node: Node,valueAttr:String, exprSuffix:String =  "expr") = new DataValueExpression {
      override val value = if( !(node \@ valueAttr).isEmpty ) Some(new StringValue(node \@ valueAttr)) else {
        if ((node \@ valueAttr + exprSuffix).isEmpty) None else Some(new ExpressionValue(node \@ valueAttr + exprSuffix))
      }
      override def localName = node.text
    }
  }

