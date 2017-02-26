package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{History, Transition}
import org.o1.espresso.machine.scxml.document.HistoryType

import scala.xml.Node


/**
  * Created by ricardo on 11/11/16.
  */
trait HistoryElement extends History {
  val histNode:Node
  def transition: Transition = TransitionElement((histNode \ "transition").head)
  override lazy val id:Option[String] = if ( (histNode \ "@id").nonEmpty ) Some(histNode \@ "id") else  None
  override lazy val historyType:HistoryType.Value = histNode \@ "type" match {
    case "deep" => HistoryType.Deep
    case _ => HistoryType.Shallow
  }
}
object HistoryElement {
  def apply(node:Node) = new HistoryElement {val histNode:Node = node}
}
