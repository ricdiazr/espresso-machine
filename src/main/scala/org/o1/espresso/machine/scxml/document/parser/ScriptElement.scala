package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Content, Script}
import org.o1.espresso.machine.scxml.document.ExecutableOn

import scala.xml.{Node, NodeSeq}

/**
  * Created by ricardo on 11/8/16.
  */
trait ScriptElement extends ExecutableElement with Script {

  def src:String = executableNode \@ "src"
  override def content = {
    if(src.isEmpty) {
      new Content {
        override def toString = executableNode.text
      }
    } else {
      new Content { override def toString = io.Source.fromURL(src).mkString}
    }
  }
}
object ScriptElement {
  def apply(node:Node, bind:ExecutableOn.Value = ExecutableOn.Ordered)= new ScriptElement with ExecutableElement {
    override val executableNode: Node = node
    override val bound = bind
  }
}
