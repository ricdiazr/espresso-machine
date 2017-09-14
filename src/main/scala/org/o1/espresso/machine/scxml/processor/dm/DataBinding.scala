package org.o1.espresso.machine.scxml.processor.dm

import org.o1.espresso.machine.scxml.document.DatamodelData

import scala.xml.Null

/**
  * Created by ricardo on 12/20/16.
  */
trait DataBinding {
  def bind[T](dmData:DatamodelData): T = Null.asInstanceOf[T]
}
