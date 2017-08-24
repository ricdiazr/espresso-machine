package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait If extends Executable {
  def localName = "if"
  def cond: String
  def elseIf: Option[Seq[Executable]] = None
  def `else`: Option[Seq[Executable]] = None
  def `then`: Seq[Executable]
}
