package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Content, DataValueExpression, Invoke, Param}

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Node, NodeSeq}

/**
  * Created by ricardo on 11/10/16.
  */
trait InvokeElement extends ExecutableElement with Invoke {
  override def src: DataValueExpression =  DataValueExpression(executableNode, "src")
  override def content:Option[Content] = {
    if ((executableNode \ "content").nonEmpty)
      Some(new Content {
        override def mime: Option[String] = Some("text/xml")

        (executableNode \ "content").text
      })
    else
      None
  }
  override def finalise: Seq[ExecutableElement] = ExecutableElement(executableNode \ "finalize")
  override def params: List[Param] = ParamElement(executableNode \ "param")
  override def id: DataValueExpression = DataValueExpression(executableNode, "id", "location")
}

object InvokeElement {
  def apply(node:Node): ExecutableElement = new InvokeElement {
    override val executableNode: Node = node
  }

  def apply(nodeSeq:NodeSeq): Seq[InvokeElement] = {
    val result = new ArrayBuffer[InvokeElement]
    nodeSeq foreach ((node:Node) => result += InvokeElement(node).asInstanceOf)
    result
  }
}
