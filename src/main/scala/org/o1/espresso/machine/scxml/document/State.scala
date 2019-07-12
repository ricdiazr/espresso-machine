package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.{DocumentElement}

/**
  * Created by ricardo on 12/20/16.
  */
trait State extends DocumentElement with NodeElement[State] {
  override val _Elm: State = this
  def localName = "state"
  lazy val id:Option[String] = None
  def stateType: StateType.Value
  def initial: Seq[String] = Nil
  def states: Seq[State] = Nil
  def executables(on:Option[ExecutableOn.Value]): Option[Seq[Executable]] = None
  def onentry:Seq[Executable]=executables(Some(ExecutableOn.Entry)).getOrElse(Nil)
  def onexit:Seq[Executable]=executables(Some(ExecutableOn.Exit)).getOrElse(Nil)
  def transitions: Seq[Transition] = Nil
  def history: Seq[History] = Nil
  def invokes: Seq[Invoke] = Nil
  def datamodel: Option[Datamodel] = None
  def finals:Seq[Final] = Nil
  def contains(descendantId:String) = false
  override def ID:Option[String]= id
  override def children: Seq[NodeElement[_]] =
    transitions.map(newFamily)++
      states.map(newFamily)++
      history.map(newFamily) ++
      onentry.map(newFamily) ++
      onexit.map(newFamily) ++
      finals.map(newFamily)
}