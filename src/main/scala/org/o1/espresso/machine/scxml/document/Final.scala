package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Final extends State with NodeElement[Final] {
  override val _Elm: Final = this
  override def localName = "final"
  def donedata: Option[Donedata]
}
