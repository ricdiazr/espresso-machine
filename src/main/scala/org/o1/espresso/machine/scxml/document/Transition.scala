package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait Transition extends DocumentElement {
  def localName = "transition"
  def event:List[String] = Nil
  def cond: String = "true"
  lazy val transitionType:TransitionType.Value = TransitionType.External
  def target: Option[String] = None
  def executables: Seq[Executable]
}
