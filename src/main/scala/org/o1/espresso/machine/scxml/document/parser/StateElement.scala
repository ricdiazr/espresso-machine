package org.o1.espresso.machine.scxml.document.parser

import org.o1.espresso.machine.scxml.document._

import scala.xml.{Node}

/**
  * Created by ricardo on 11/8/16.
  */
trait StateElement extends State {
  val state:Node

  lazy val stateType = state.label match {
    case "initial" => StateType.Initial
    case "parallel" => StateType.Parallel
    case _ => if (state.count((n: Node) => (n.label == "state" || n.label == "initial" || n.label == "parallel")) > 0)
      StateType.Compound
    else
      StateType.Atomic
  }
  override lazy val id = if ( (state \ "@id").nonEmpty ) Some(state \@ "id") else None
  override def states: Seq[State] = state.child.filter(
    (n:Node) => n.label == "initial" || n.label == "state" || n.label == "parallel").map(StateElement.apply)

  override def executables(on: Option[ExecutableOn.Value]): Option[Seq[ExecutableElement]] = {
    on match {
      case ExecutableOn.Entry => Some(ExecutableElement(state \ "onentry",ExecutableOn.Entry))

      case ExecutableOn.Exit => Some(ExecutableElement(state \ "onexit", ExecutableOn.Exit))
      case _ => Some(ExecutableElement(state))
    }
  }
  override def initial: Seq[String] = if ( (state \ "@initial").nonEmpty ) (state \@"initial").split(' ') else super.initial
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
