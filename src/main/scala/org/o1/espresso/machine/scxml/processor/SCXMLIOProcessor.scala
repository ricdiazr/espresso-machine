package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.DocumentElementProcessor
import org.o1.espresso.machine.scxml.document.{Raise, SCXML, Send}
import org.o1.logging.Logging

/**
  * Created by ricardo on 1/29/17.
  */
trait SCXMLIOProcessor extends DocumentElementProcessor[Send,SCXMLEventInstance] with Logging {
  this: SCXMLEventListener with SCXMLEventDispatcher =>

  def process(element:E,id:Option[String]=None):SCXMLEventInstance
}
