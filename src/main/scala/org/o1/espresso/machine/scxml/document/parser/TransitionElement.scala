package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document.{Executable, Transition, TransitionType}

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Node, NodeSeq}

/**
  * Created by ricardo on 11/11/16.
  */
trait TransitionElement extends Transition {
  val transitionNode:Node

  override def event:List[String] = {
    if ( (transitionNode \ "@event").nonEmpty )
      (transitionNode \@ "event").split(" ").toList
    else Nil
  }
  override def cond: String = if ( (transitionNode \ "@cond").nonEmpty ) transitionNode \@ "cond" else  "true"
  override lazy val transitionType:TransitionType.Value = transitionNode \@ "type" match {
    case "internal" => TransitionType.Internal
    case _ => TransitionType.External
  }
  override def target: Option[String] = if ( (transitionNode \ "@target").nonEmpty ) Some(transitionNode \@ "target") else None
  override def executables: Seq[Executable] = ExecutableElement(transitionNode \ "_")
}
object TransitionElement {
  def apply(node:Node) = new TransitionElement {
    override val transitionNode: Node = node
  }
  def apply(nodes:NodeSeq): Seq[TransitionElement] = {
    val tArray = new ArrayBuffer[TransitionElement]
    nodes foreach ( (node:Node) => tArray+=TransitionElement(node))
    tArray
  }
}