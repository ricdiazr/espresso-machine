package org.o1.espresso.machine.scxml.document.parser

import java.net.URL

import org.o1.espresso.machine.scxml.document.{BindingType, Final, SCXML, State}
import org.o1.espresso.machine.scxml.InvalidDocumentElementException

import scala.xml.{Elem, Node, XML}

/**
  * Created by ricardo on 11/8/16.
  */
trait SCXMLDocument extends SCXML {
  val document:Elem

  override lazy val name:Option[String] = if ((document \@"name").isEmpty) None else Some((document \@"name"))
  override def binding = {
    if ((document \@"binding").isEmpty) super.binding else (document \@"binding") match {
      case "late" => BindingType.late
      case _ => BindingType.early
    }
  }
  override def initial = if ((document \@"initial").isEmpty) super.initial else Some(document \@"initial")
  override def datamodel = DatamodelElement(document)
  override def script = {
    if((document \ "script").nonEmpty) Some(ScriptElement((document \ "script").head).asInstanceOf)
    else None
  }
  override def states: Seq[State] = document.child.filter(
    (n:Node) => n.label == "initial" || n.label == "state" || n.label == "parallel").map(StateElement.apply)

  override def finals:Seq[Final] = FinalElement(document \ "final")

  def scxml(url:URL, loader: (URL) => Elem): Elem = {

    val docroot:Elem = loader(url)
// TODO: xml namespace support
    // val ns = Namespace(xmlns)
    if( version != (docroot \@ "version"))
      throw InvalidDocumentElementException((docroot \@ "xmlns"), localName, "unsupported scxml document")
    else
      docroot
  }
}

object SCXMLDocument {
  def apply(resource: String, l: (URL) => Elem = XML.load) = new SCXMLDocument {
    val document: Elem = scxml(new URL(resource), l)
  }
}
