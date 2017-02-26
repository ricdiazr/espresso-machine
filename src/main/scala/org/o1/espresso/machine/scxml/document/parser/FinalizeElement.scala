package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Executable, Finalize}
import org.o1.espresso.machine.scxml.document.ExecutableOn

import scala.xml.Node

/**
  * Created by ricardo on 11/10/16.
  */
trait FinalizeElement extends ExecutableElement with Finalize {

  override def executables: Seq[Executable] = ExecutableElement(executableNode \ "_")
}

object FinalizeElement {
  def apply(node:Node,bind:ExecutableOn.Value = ExecutableOn.Ordered): ExecutableElement = new FinalizeElement {
    override val executableNode: Node = node
    override val bound = bind
  }
}
