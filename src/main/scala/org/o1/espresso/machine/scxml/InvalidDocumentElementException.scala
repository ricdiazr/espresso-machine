package org.o1.espresso.machine.scxml

/**
  * Created by ricardo on 12/20/16.
  */
case class InvalidDocumentElementException(ns:String, name:String, msg:String) extends Exception("<" + ns + ":" + name + "--> " + msg)