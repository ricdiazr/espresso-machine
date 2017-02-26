package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait Final extends DocumentElement {
  def localName = "final"
  def executables(on:Option[ExecutableOn.Value]): Seq[Executable] = Nil
  def donedata: Option[Donedata]
}
