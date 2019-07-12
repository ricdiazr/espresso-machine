package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml._

/**
  * Created by ricardo on 12/20/16.
  */
trait SCXML extends DocumentElement with NodeElement[SCXML] {
  override val _Elm: SCXML = this
  def localName: String = "scxml"
  lazy val name:Option[String] = None
  def version: String ="1.0"
  def binding:BindingType.Value = BindingType.early
  def initial:Option[String] = None
  def datamodel: Datamodel
  def script: Option[Script]
  def states: Seq[State]
  def finals: Seq[Final]

  override def children: Seq[NodeElement[_]] = states.map(newFamily) ++ finals.map(newFamily)
}
