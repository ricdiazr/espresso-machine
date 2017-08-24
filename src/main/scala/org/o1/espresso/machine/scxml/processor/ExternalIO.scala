package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.ProcessDescriptor
import org.o1.espresso.machine.scxml.document.{Content, Param}

/**
  * Created by ricardo on 12/20/16.
  * transports messages between none local [[SCXMLInstance]]S
  */
trait ExternalIO {
  def snd(event:String,
          target:Option[String],
          delay:Option[String],
          content:Option[Content],
          params:Param*):ProcessDescriptor
  def rcv(event:String,
          src:Option[String],
          target:String,
          content:Option[Content],
          params:Param*):ProcessDescriptor
}
