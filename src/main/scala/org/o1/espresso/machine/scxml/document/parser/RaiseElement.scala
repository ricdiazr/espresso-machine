package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.ExecutableOn
import org.o1.espresso.machine.scxml.document.Raise

import scala.xml.Node

/**
  * Created by ricardo on 11/10/16.
  */
trait RaiseElement extends ExecutableElement with Raise {
  override def event = DataValueExpression(executableNode, "event")
}

object RaiseElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new RaiseElement {
    val executableNode: Node = node
    override val bound = bind
  }
}
