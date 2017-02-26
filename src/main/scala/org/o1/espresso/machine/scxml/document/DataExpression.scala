package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
case class DataExpression(override val id: Option[String]=None,val expr:String) extends DatamodelData
