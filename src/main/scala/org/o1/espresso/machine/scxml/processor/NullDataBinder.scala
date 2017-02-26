package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.Datamodel

/**
  * Created by ricardo on 12/20/16.
  */
class NullDataBinder extends DataBinding {
  type Data = Null
  def bind(dm:Datamodel):Set[Data] = Set[Data]()
}
