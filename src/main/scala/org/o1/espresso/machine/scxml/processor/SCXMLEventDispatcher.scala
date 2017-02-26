package org.o1.espresso.machine.scxml.processor

/**
  * Created by ricardo on 1/25/17.
  */
trait SCXMLEventDispatcher {
  def dispatch(e:Event):Unit
}
