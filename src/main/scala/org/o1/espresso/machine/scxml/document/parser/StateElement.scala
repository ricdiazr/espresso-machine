package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document._

import scala.xml.{Node}

/**
  * Created by ricardo on 11/8/16.
  */
trait StateElement extends State {
  val state:Node

  override def stateType = state.label match {
    case "initial" => StateType.Initial
    case "parallel" => StateType.Parallel
    case _ => if (state.count((n: Node) => (n.label == "state" || n.label == "initial" || n.label == "parallel" || n.label == "history")) > 0)
      StateType.Compound
    else
      StateType.Atomic
  }
  override lazy val id = if ( (state \ "@id").nonEmpty ) Some(state \@ "id") else None
  override def states: Seq[State] = state.child.filter(
    (n:Node) => n.label == "initial" || n.label == "final" || n.label == "state" || n.label == "parallel"  || n.label == "history").map(
    (n:Node) => n.label match {
      case "history" => HistoryElement(n)
      case "final" => FinalElement(n)
      case _ => StateElement(n)
    })

  override def executables(on: Option[ExecutableOn.Value]): Option[Seq[ExecutableElement]] = {
    on match {
      case ExecutableOn.Entry => Some(ExecutableElement(state \ "onentry",ExecutableOn.Entry))

      case ExecutableOn.Exit => Some(ExecutableElement(state \ "onexit", ExecutableOn.Exit))
      case _ => None
    }
  }
  override def initial: Seq[String] = if ( (state \ "@initial").nonEmpty ) (state \@"initial").split(' ') else super.initial
  override def invokes: Seq[Invoke] = InvokeElement(state \ "invoke")
  override def transitions = TransitionElement(state \ "transition")
  override def history: Seq[History] =state.child.filter((n:Node) => n.label == "history").map(HistoryElement.apply)
  override def datamodel: Option[Datamodel] = if( (state \ "datamodel").nonEmpty ) Some(DatamodelElement(state \ "datamodel")) else None
  override def finals:Seq[Final] = FinalElement(state \ "final")
  override def contains(descendantId:String) =state.exists((n) => n \ "@id" == descendantId)
  override def children: Seq[NodeElement[_]] = states.map(newFamily)
}
object StateElement {
  def apply(node:Node) = new StateElement {
    override val state: Node = node
  }
}
