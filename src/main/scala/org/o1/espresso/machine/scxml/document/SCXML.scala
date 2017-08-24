package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml._

/**
  * Created by ricardo on 12/20/16.
  */
trait SCXML extends DocumentElement {
  def localName: String = "scxml"
  lazy val name:Option[String] = None
  def version: String ="1.0"
  def binding:String = "early"
  def initial:Option[String] = None
  def datamodel: Datamodel
  def script: Script
  def states: Seq[State]
  def finals: Seq[Final]
}
