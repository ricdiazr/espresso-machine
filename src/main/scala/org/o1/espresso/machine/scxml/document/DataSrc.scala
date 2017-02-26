package org.o1.espresso.machine.scxml.document

import java.net.URI

/**
  * Created by ricardo on 12/20/16.
  */
case class DataSrc(override val id: Option[String]=None, val src:URI) extends DatamodelData
