package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement

/**
  * Created by ricardo on 12/20/16.
  */
trait Content extends DocumentElement {
  def localName: String = "content"
  def mime:Option[String] = None
  def ns:Option[String] = None
}
