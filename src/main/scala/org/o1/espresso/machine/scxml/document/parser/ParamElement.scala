package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.Param

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.xml.{Node, NodeSeq}

/**
  * Created by ricardo on 11/9/16.
  */
trait ParamElement extends Param {
  val paramNode: Node
  override def expr = if ( (paramNode \ "@expr").nonEmpty ) Some(paramNode \@ "expr") else None
  override def location = if ( expr.isEmpty ) Some(paramNode \@ "expr") else None
}
object ParamElement {
  def apply(node:Node) = new ParamElement {
    override val paramNode: Node = node
  }

  def apply(paramNodeSeq:NodeSeq) : List[ParamElement] = {
    val paramsa = new ListBuffer[ParamElement]
    paramNodeSeq foreach ((node:Node)=> paramsa += ParamElement(node))
    paramsa.toList
  }
}
