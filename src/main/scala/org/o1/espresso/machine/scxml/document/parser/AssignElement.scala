package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Assign, Content}
import org.o1.espresso.machine.scxml.document.ExecutableOn

import scala.xml.Node

/**
  * Created by ricardo on 11/9/16.
  */
trait AssignElement extends ExecutableElement with Assign {

  override def location = executableNode \@ "location"
  override def expr = if( (executableNode \ "@expr").nonEmpty ) Some(executableNode \@ "expr") else None
  override def inline = expr match {
    case Some(x) => Some(new Content {override def toString=x})
    case None => Some(new Content{override def toString=executableNode.text})
  }
}

object AssignElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new AssignElement {
    val executableNode: Node = node
    override val bound = bind
  }
}
