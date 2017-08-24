package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement

/**
  * Created by ricardo on 12/20/16.
  */
trait Donedata extends DocumentElement {
  def localName: String = "donedata"
  def content: Option[Content] = None
  def params:List[Param] = Nil
}
