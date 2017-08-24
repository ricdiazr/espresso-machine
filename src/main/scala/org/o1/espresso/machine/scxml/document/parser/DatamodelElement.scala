package org.o1.espresso.machine.scxml.document.parser

import java.net.URI

import org.o1.espresso.machine.scxml.document._

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, NodeSeq}

/**
  * Created by ricardo on 11/8/16.
  */
trait DatamodelElement extends Datamodel {
  val dataNodes: NodeSeq
  val modelType:String
  override def datas(ids: String*): Seq[DatamodelData] = {
    val darray = if (ids.length > 1 || ids.contains("*"))
      new ArrayBuffer[DatamodelData](ids.length) else new ArrayBuffer[DatamodelData](dataNodes.length)
    dataNodes foreach ((node: NodeSeq) =>
      if (ids.isEmpty || ids.contains("*") || ids.contains((node \@ "id"))) darray += dataModelData(node) else darray)
    darray
  }

  def dataModelData(data:NodeSeq): DatamodelData = {
    data \@"_" match {
      case "src" => Src(id= (data \@"id"), URI.create((data \@"src")), model=modelType)
      case "expr" => Expr(id= (data \@"id"), expr = ExpressionValue(data \@"expr"), model=modelType)
    }
  }
}
object DatamodelElement {
  def apply(scxml:Elem):DatamodelElement = apply(scxml \ "datamodel",scxml \@"datamodel")
  def apply(datamodel:NodeSeq, model:String = "ecmascript") = new DatamodelElement {
    val dataNodes:NodeSeq = datamodel \ "data"
    val modelType = model
  }
}
