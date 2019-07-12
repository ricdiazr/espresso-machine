package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{DataValueExpression, ExprStringValue, StringValue}

import scala.io.Source

/**
  * Created by ricardo on 3/11/17.
  */
trait SCXMLDataValueExpressionEvaluator {

  def eval(context:Map[String,AnyVal], xpr:String): Either[String, Source]

  def eval(xpre:DataValueExpression)(context:Map[String,AnyVal]): AnyRef = xpre.value match {
    case Some(s) => (s:ExprStringValue) match {
      case s:StringValue => (context:Map[String,AnyVal]) => s.toString
      case _ => eval(context,s.toString) //assumes it is an expression
    }
    case None => throw NonConformantSCXMLException(
        xpre.localName,
        "should include either value or expression")
  }
}