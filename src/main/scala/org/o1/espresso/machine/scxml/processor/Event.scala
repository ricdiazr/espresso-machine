package org.o1.espresso.machine.scxml.processor

import java.util.EventObject

import org.o1.espresso.machine.StateMachineProcess
import org.o1.espresso.machine.scxml.document.{Content, Param}

/**
  * Created by ricardo on 12/20/16.
  */
class Event(val src:StateMachineProcess,
            val descriptor:String,
            val target:Option[String] = None,
            val params:List[Param] = Nil,
            val content:Option[Content] = None,
            val isExternal:Boolean = false) extends EventObject(src) {
  def apply(src:StateMachineProcess,
            descriptor:String) = new Event(src,descriptor)
  def apply(src:StateMachineProcess,
            descriptor:String,
            target:Option[String],
            params:List[Param],
            content:Option[Content],
            isExternal:Boolean) = new Event(src,descriptor, target, params,content, isExternal)
}
