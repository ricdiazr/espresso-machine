package org.o1.espresso.machine.scxml.document.parser

import java.net.URL

import org.o1.espresso.machine.scxml.document.{Final, SCXML, State}
import org.o1.espresso.machine.scxml.InvalidDocumentElementException

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node, XML}

/**
  * Created by ricardo on 11/8/16.
  */
trait SCXMLDocument extends SCXML {
  val document:Elem

  override lazy val name:Option[String] = if ((document \@"name").isEmpty) None else Some((document \@"name"))
  override def binding = if ((document \@"binding").isEmpty) super.binding else (document \@"binding")
  override def initial = if ((document \ "@intial").nonEmpty) super.initial else Some(document \@"intial")
  override def datamodel = DatamodelElement(document)
  override def script = ScriptElement((document \ "script").head).asInstanceOf
  override def states: Seq[State] = {
    val statesArray = new ArrayBuffer[State]
    document.foreach( (node:Node) =>
      if ( node.label == "initial" || node.label == "state" || node.label == "parallel" ) statesArray+=StateElement(node))

    statesArray
  }
  override def finals:Seq[Final] = FinalElement(document \ "final")

  def scxml(url:URL, loader: (URL) => Elem): Elem = {
    val docroot:Elem = loader(url)
    if(xmlns != (docroot \@ "xmlns") || version != (docroot \@ "version"))
      throw InvalidDocumentElementException((docroot \@ "xmlns"), localName, "unsupported scxml document")
    else
      docroot
  }
}

object SCXMLDocument {
  def apply(res: String, l: (URL) => Elem = XML.load) = new SCXMLDocument {
    val document: Elem = scxml(new URL(res), l)
  }
}
