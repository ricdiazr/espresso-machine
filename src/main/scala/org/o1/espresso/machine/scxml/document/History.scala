package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait History extends DocumentElement {
  def localName = "history"
  def transition: Transition
  lazy val id:Option[String] = None
  lazy val historyType:HistoryType.Value = HistoryType.Shallow
}
