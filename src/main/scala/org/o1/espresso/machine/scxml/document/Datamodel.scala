package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement

/**
  * Created by ricardo on 12/20/16.
  */
trait Datamodel extends DocumentElement {
  def localName: String = "datamodel"
  def datas(ids:String*): Seq[DatamodelData]
}
