package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Raise extends Executable {
  def localName = "raise"
  def event: String
}