package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.Datamodel

/**
  * Created by ricardo on 12/20/16.
  */
trait DataBinding {
  type Data
  def bind(dm:Datamodel):Set[Data]
}
