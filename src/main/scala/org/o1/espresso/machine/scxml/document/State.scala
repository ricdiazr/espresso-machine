package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait State extends DocumentElement {
  def localName = "state"
  lazy val id:Option[String] = None
  val stateType: StateType.Value
  def states: Seq[State] = Nil
  def executables(on:Option[ExecutableOn.Value]): Option[Seq[Executable]] = None
  def transitions: Seq[Transition] = Nil
  def history: Option[History] = None
  def invokes: Seq[Invoke] = Nil
  def datamodel: Option[Datamodel] = None
  def finals:Seq[Final] = Nil
}
