package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.{DocumentElement, InvalidDocumentElementException}
import org.o1.espresso.machine.scxml.document.{DatamodelData, State, _}
import org.o1.logging.Logging

import scala.annotation.tailrec

trait SCXMLElementInterpreter {
  this: Logging =>

  def bind[T](d: DatamodelData): T

  def isDocumentValid(scxml: SCXML): Boolean = {
    if (scxml.states.size >= 1) true
    else if (scxml.finals.size >= 1) true
    else scxml.initial match {
      case Some(i) => scxml.states.count((s: State) => s.id == i) == 1
      case _ => false
    }
  }

  def datamodel[T](state: State): SCXMLDatamodel[T] = {
    state.datamodel match {
      case Some(dm) => datamodel(dm)
      case _ => SCXMLDatamodel[T]
    }
  }

  def datamodel[T](dm: Datamodel, t: BindingType.Value = BindingType.early): SCXMLDatamodel[T] = {
    t match {
      case BindingType.early => SCXMLDatamodel[T](dm, (d: DatamodelData) => bind(d), Some(false))
      case _ => SCXMLDatamodel[T](dm, (d: DatamodelData) => bind(d))
    }
  }

  def selectInitialState(scxml: SCXML): State = {
    scxml.initial match {
      case Some(x) => scxml.states.filter(_.id == x).head
      case None => scxml.states.head
    }
  }

  def selectInitialTransitions(state: State,deep:Integer=0): Seq[Transition] = {

    def recSelect(st: State, result: Seq[Transition], d:Integer): Seq[Transition] = state.stateType match {
      case StateType.Atomic => Nil
      case StateType.Initial => result ++ st.transitions
      case StateType.Compound => if(d < deep) st.states.flatMap((s) => recSelect(s, result,d+1)) else Nil
    }
    recSelect(state, Nil,0)
  }

  def computeEntrySet(t: NodeElement[Transition]): Set[NodeElement[State]] = {
    @tailrec
    def targets(source: NodeElement[_], stateIds: Set[String], result: Set[NodeElement[_]]):
    Set[NodeElement[_]] = {
      val nodeSet = result ++ source.children.filter((n: NodeElement[_]) => n.ID match {
        case Some(i) => stateIds.contains(i)
        case _ => false
      })
      val found: Set[String] = nodeSet.map((n: NodeElement[_]) => n.ID.getOrElse(""))
      if (found.size == stateIds.size) nodeSet else {
        source.parent match {
          case Some(p) => targets(p, stateIds.filterNot((id: String) => found.contains(id)), nodeSet)
          case _ => throw InvalidDocumentElementException(
            source().asInstanceOf[DocumentElement].xmlns
            , source().asInstanceOf[DocumentElement].localName,
            "this element should not be an orphan")
        }
      }
    }

    t.parent match {
      case Some(p) => for {
        nodes: Set[NodeElement[_]] <- targets(p, t().target.toSet, Set())
        statesToEnter: Set[NodeElement[State]] <- nodes.flatMap(
          (n: NodeElement[_]) => addDescendantStatesToEnter(n))
      } yield statesToEnter
      case None => Set()
    }
  }

  def addDescendantStatesToEnter(node: NodeElement[_],
                                 statesToEnter: Set[NodeElement[State]] = Set[NodeElement[State]]()):
  Set[NodeElement[State]] = {
    val result:Set[NodeElement[State]] = for {
      s: State <- node()
      c: Set[NodeElement[State]] <- s.stateType match {
        case StateType.Parallel =>
        case StateType.Compound =>
          if (s.initial.length > 0 && s.stateType != StateType.Initial)
            s.children.filter((e) => s.initial.contains(e.ID)).flatMap((n)=>addDescendantStatesToEnter(n,result))
          else
            selectInitialTransitions(s).flatMap(
              (t) => s.children.filter((e) => t.target.contains(e.ID))).flatMap(
              (n)=>addDescendantStatesToEnter(n,result))
        case _ => Nil
      }
    } yield Set(node) ++ c
    statesToEnter ++ result
  }


  def selectTransitions(p:(State)=>Boolean, s:State, t:(Transition) => Boolean, skip:Integer=0, deep:Integer=0):
  Seq[Transition] = {
    if(skip < 0 || deep < 0) new IllegalArgumentException
    if(skip == 0) {
      val result:Seq[Transition]=s.states.filter(child => p(child)).flatMap(child => child.transitions.filter(t))
      if(deep>0) {
        result ++ s.states.filter(child => p(child)).flatMap(child => selectTransitions(p,child,t,skip,deep-1))
      }
      result
    } else {
      s.states.flatMap(state => {
        selectTransitions(p,state,t, skip-1,deep)
      })
    }
  }
}
