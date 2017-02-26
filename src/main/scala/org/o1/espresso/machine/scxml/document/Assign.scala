package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Assign extends Executable {
  def localName = "assign"
  def location: String
  def expr: Option[String] = None
  def inline: Option[Content] = None

}
