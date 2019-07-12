package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Donedata, ExecutableOn, Final, StateType}

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Node, NodeSeq}


/**
  * Created by ricardo on 11/10/16.
  */
trait FinalElement extends Final {

  val finalNode:Node

  override def executables(on: Option[ExecutableOn.Value]): Seq[ExecutableElement] = {
    on match {
      case ExecutableOn.Entry => ExecutableElement(finalNode \ "onentry",ExecutableOn.Entry)
      case ExecutableOn.Exit => ExecutableElement(finalNode \ "onexit", ExecutableOn.Exit)
      case _ =>Nil
    }
  }
  override def donedata: Option[Donedata] = {
    if( (finalNode \ "donedata").nonEmpty ) Some(DonedataElement((finalNode \ "donedata").head)) else None
  }
}
object FinalElement {
  def apply(node:Node) = new FinalElement {
    override val finalNode: Node = node
    override def stateType = StateType.Atomic
  }
  def apply(nodes:NodeSeq): Seq[FinalElement] = {
    val result = new ArrayBuffer[FinalElement]
    nodes foreach ((node:Node) => result += FinalElement(node))
    result
  }
}