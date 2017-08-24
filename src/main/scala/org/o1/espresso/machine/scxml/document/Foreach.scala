package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Foreach extends Executable {
  def localName = "foreach"
  def array: String
  def item: String
  def index: Option[String] = None
  def `do`: Seq[Executable]
}
