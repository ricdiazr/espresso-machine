package org.o1.espresso.machine.scxml.processor
/**
  * Created by ricardo on 12/20/16.
  */
case class NonConformantSCXMLException(name:String, msg:String) extends Exception(msg)
