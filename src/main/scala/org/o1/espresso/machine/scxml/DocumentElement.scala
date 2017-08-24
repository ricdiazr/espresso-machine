package org.o1.espresso.machine.scxml

/**
  * Created by ricardo on 12/20/16.
  */
trait DocumentElement {
  def xmlns: String = "http://www.w3.org/2005/07/scxml"
  def localName:String
}
