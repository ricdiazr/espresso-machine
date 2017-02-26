package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Content, Donedata, Param}

import scala.xml.Node

/**
  * Created by ricardo on 11/11/16.
  */
trait DonedataElement extends Donedata {
  val doneNode:Node
  override def content: Option[Content] = {
    if ((doneNode \ "content").nonEmpty)
      Some(new Content {
        override def mime: Option[String] = Some("text/xml")
        (doneNode \ "content").text
      })
    else
      None
  }
  override def params:List[Param] = ParamElement(doneNode \ "param")
}

object DonedataElement {
  def apply(node:Node) = new DonedataElement {
    override val doneNode: Node = node
  }
}