package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Script extends Executable {
  def localName = "script"
  def content: Content
}
