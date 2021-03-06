package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.ExecutableOn
import org.o1.espresso.machine.scxml.document.Log

import scala.xml.Node

/**
  * Created by ricardo on 11/10/16.
  */
trait LogElement extends ExecutableElement with Log {
  override def label = executableNode \@ "label"
  override def expr = executableNode \@ "expr"
}

object LogElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new LogElement {
    val executableNode: Node = node
    override val bound = bind
  }
}