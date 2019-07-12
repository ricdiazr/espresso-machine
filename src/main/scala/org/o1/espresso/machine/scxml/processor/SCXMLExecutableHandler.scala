package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{Executable, NodeElement}

trait SCXMLExecutableHandler[E <: Executable] {
  def execute(context:SCXMLDatamodel[_], contextNode:NodeElement[E], filter:(E)=>Boolean= true): SCXMLDatamodel[_] {

  }

}
