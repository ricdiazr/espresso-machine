package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Executable, ExecutableOn}

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Node, NodeSeq, Null}

/**
  * Created by ricardo on 11/9/16.
  */
trait ExecutableElement extends Executable {
  val executableNode:Node

  def executableSequence(nodeSeq:NodeSeq,bind:ExecutableOn.Value = ExecutableOn.Ordered,
                         execel:(Node,ExecutableOn.Value) => ExecutableElement = ExecutableElement.apply): Seq[ExecutableElement] = {
    val result = new ArrayBuffer[ExecutableElement]
    nodeSeq foreach ((node:Node) => result += execel(node,bind))
    result
  }
}

object ExecutableElement {
    def apply(node:Node, bind:ExecutableOn.Value): ExecutableElement = node.label match {
          case "foreach" => ForeachElement(node,bind)
          case "assign" => AssignElement(node,bind)
          case "invoke" => InvokeElement(node)
          case "script" => ScriptElement(node,bind)
          case "raise" => RaiseElement(node,bind)
          case "send" => SendElement(node,bind)
          case "log" => LogElement(node,bind)
          case "if" => IfElement(node,bind)
        }
  def apply(nodeSeq:NodeSeq, bind:ExecutableOn.Value = ExecutableOn.Ordered): Seq[ExecutableElement] = new ExecutableElement {
    val executableNode:Node = Null.asInstanceOf[Node]
    override def localName: String = Null.asInstanceOf[String]
  }.executableSequence(nodeSeq,bind)
}
