package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.ExecutableOn
import org.o1.espresso.machine.scxml.document.Foreach

import scala.xml.Node

/**
  * Created by ricardo on 11/10/16.
  */
trait ForeachElement extends ExecutableElement with Foreach {
  override def array = executableNode \@ "array"
  override def item = executableNode \@ "item"
  override def index = if ( (executableNode \ "@index").nonEmpty ) Some(executableNode \@ "index") else None
  override def `do` = executableSequence(executableNode \ "_", ExecutableOn.Entry)
}

object ForeachElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new ForeachElement {
    override val executableNode: Node = node
    override val bound = bind
  }
}
