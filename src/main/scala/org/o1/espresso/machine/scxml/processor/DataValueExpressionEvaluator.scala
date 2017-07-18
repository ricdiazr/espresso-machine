package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.{DocumentElement, InvalidDocumentElementException}
import org.o1.espresso.machine.scxml.document.{DataValueExpression, SCXML}

import scala.io.Source

/**
  * Created by ricardo on 3/11/17.
  */
trait DataValueExpressionEvaluator {

  def eval(context:Map[String,AnyVal], xpr:String): Either[String, Source]

  def eval(xpre:DataValueExpression)(context:Map[String,AnyVal]): AnyRef = {
    xpre.value match {
      case Some(v) => v
      case None => xpre.valuexpr match {
        case Some(x) => eval(context,x).fold(_.toString, _.toString)
        case None => throw InvalidDocumentElementException(
          xpre.xmlns,
          xpre.localName,
          "should include either value or expression")
      }
    }
  }
}