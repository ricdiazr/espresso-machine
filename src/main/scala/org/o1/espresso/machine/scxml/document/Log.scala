package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Log extends Executable {
  def localName = "log"
  def label: String
  def expr: String
}
