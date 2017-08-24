package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement

/**
  * Created by ricardo on 12/20/16.
  */
trait Param extends DocumentElement {
  def localName: String = "param"
  def expr:Option[String] = None
  def location:Option[String] = None
}
