package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.If
import org.o1.espresso.machine.scxml.document.ExecutableOn
import scala.xml.Node

/**
  * Created by ricardo on 11/9/16.
  */
trait IfElement extends ExecutableElement with If {

  override def cond = executableNode \@ "cond"
  override def `elseIf` = Some(executableSequence(executableNode \ "elseif", execel = IfElement.apply))
  override def `else` = if ((executableNode \ "else").nonEmpty) Some(executableSequence(executableNode \ "else")) else None
  override def `then` = if ((executableNode \ "_").nonEmpty) executableSequence(executableNode \ "_") else Nil
}

object IfElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered):  ExecutableElement =
  new IfElement {
    val executableNode: Node = node
    override val bound = bind
  }
}
