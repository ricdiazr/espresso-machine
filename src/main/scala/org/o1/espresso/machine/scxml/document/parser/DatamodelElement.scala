package org.o1.espresso.machine.scxml.document.parser

import java.net.URI

import org.o1.espresso.machine.scxml.document.{DataExpression, DataSrc, Datamodel, DatamodelData}
import org.o1.espresso.machine.scxml.document.DatamodelData

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, NodeSeq}

/**
  * Created by ricardo on 11/8/16.
  */
trait DatamodelElement extends Datamodel {
  val dataNodes: NodeSeq

  override def datas(ids: String*): Seq[DatamodelData] = {
    val darray = if (ids.length > 1 || ids.contains("*"))
      new ArrayBuffer[DatamodelData](ids.length) else new ArrayBuffer[DatamodelData](dataNodes.length)
    dataNodes foreach ((node: NodeSeq) =>
      if (ids.isEmpty || ids.contains("*") || ids.contains((node \@ "id"))) darray += dataModelData(node) else darray)
    darray
  }

  def dataModelData(data:NodeSeq): DatamodelData = {
    data \@"_" match {
      case "src" => DataSrc(id= Some((data \@"id")), URI.create((data \@"src")))
      case "expr" => DataExpression(id= Some((data \@"id")), expr = (data \@"expr"))
    }
  }
}
object DatamodelElement {
  def apply(scxml:Elem):DatamodelElement = apply(scxml \ "datamodel")
  def apply(datamodel:NodeSeq) = new DatamodelElement{val dataNodes:NodeSeq = datamodel \ "data"}
}
