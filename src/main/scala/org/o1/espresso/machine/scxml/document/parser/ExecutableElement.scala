package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.InvalidDocumentElementException
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
          case "onentry" => apply(node,ExecutableOn.Entry)
          case "onexit" => apply(node,ExecutableOn.Exit)
          case _=> throw InvalidDocumentElementException(
            (node \@ "xmlns"), node.label, "unsupported scxml document")
        }
  def apply(nodeSeq:NodeSeq, bind:ExecutableOn.Value = ExecutableOn.Ordered): Seq[ExecutableElement] = new ExecutableElement {
    override val executableNode:Node = nodeSeq.asInstanceOf[Node]
    override def localName: String = executableNode.label
    println(s"building ${localName}")
  }.executableSequence(nodeSeq,bind)
}
