package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document._

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Node, NodeSeq}

/**
  * Created by ricardo on 11/8/16.
  */
trait StateElement extends State {
  val state:Node
  val stateType = state.label match {
    case "initial" => StateType.Initial
    case "parallel" => StateType.Parallel
    case _ => if (state.count((n: Node) => (n.label == "state" || n.label == "initial" || n.label == "parallel")) > 0)
      StateType.Compound
    else
      StateType.Atomic
  }
  override lazy val id = if ( (state \ "@id").nonEmpty ) Some(state \@ "id") else None
  override def states: Seq[State] = {
    val statesArray = new ArrayBuffer[State]
    state.foreach( (node:Node) =>
      if ( node.label == "initial" || node.label == "state" || node.label == "parallel" ) statesArray+=StateElement(node))

    statesArray
  }
  override def executables(on: Option[ExecutableOn.Value]): Option[Seq[ExecutableElement]] = {
    on match {
      case ExecutableOn.Entry => Some(ExecutableElement(state \ "onentry",ExecutableOn.Entry))

      case ExecutableOn.Exit => Some(ExecutableElement(state \ "onexit", ExecutableOn.Exit))
      case _ => Some(ExecutableElement(state))
    }
  }
  override def  invokes: Seq[Invoke] = InvokeElement(state \ "invoke")
  override def transitions = TransitionElement(state \ "transition")
  override def history: Option[History] = if ( (state \ "history").nonEmpty ) Some(HistoryElement((state \ "history").head)) else None
  override def datamodel: Option[Datamodel] = if( (state \ "datamodel").nonEmpty ) Some(DatamodelElement(state \ "datamodel")) else None
  override def finals:Seq[Final] = FinalElement(state \ "final")
}
object StateElement {
  def apply(node:Node) = new StateElement {
    override val state: Node = node
  }
}
