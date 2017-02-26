package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Finalize extends Executable {
  def localName = "finalize"
  def id:Option[String] = None
  def executables: Seq[Executable]
}
