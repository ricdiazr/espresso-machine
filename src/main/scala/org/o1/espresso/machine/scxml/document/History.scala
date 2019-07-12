package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait History extends State with NodeElement[History] {
  override val _Elm:History = this
  override def localName = "history"
  def transition: Transition
  lazy val historyType:HistoryType.Value = HistoryType.Shallow
  override def ID = id
  override def children: Seq[NodeElement[_]] = Seq(newFamily(transition))
}
