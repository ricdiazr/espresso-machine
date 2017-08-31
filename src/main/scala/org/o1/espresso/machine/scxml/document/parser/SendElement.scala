package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Content, ExecutableOn, Send}

import scala.xml.Node

/**
  * Created by ricardo on 11/10/16.
  */
trait SendElement extends ExecutableElement with Send {

  override def event = DataValueExpressionAttribute(executableNode,"event")
  override def transportType = DataValueExpressionAttribute(executableNode, "type")
  override def target = DataValueExpressionAttribute(executableNode,"target")
  override def id = DataValueExpressionAttribute(executableNode,"id","location")
  override def delay = DataValueExpressionAttribute(executableNode, "delay")
  override def content = new Content{override def toString=executableNode.text}

  override def params = ParamElement(executableNode \ "param")
}
object SendElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new SendElement {
    val executableNode: Node = node
    override val bound = bind
  }
}
