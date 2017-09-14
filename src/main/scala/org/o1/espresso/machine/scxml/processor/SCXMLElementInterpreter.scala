package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{DatamodelData, _}
import org.o1.logging.Logging

trait SCXMLElementInterpreter { this: Logging =>

  def bind[T](d:DatamodelData): T

  def isDocumentValid(scxml:SCXML):Boolean = {
    if (scxml.states.size >= 1) true
    else if (scxml.finals.size >= 1 ) true
    else scxml.initial match {
      case Some(i) => scxml.states.count((s:State)=> s.id == i) == 1
      case _ => false
    }
  }

  def datamodel[T](t:BindingType.Value,dm:Datamodel): SCXMLDatamodel[T] = {
    t match {
      case BindingType.early => SCXMLDatamodel[T](dm,(d:DatamodelData) => bind(d),Some(false))
      case _=> SCXMLDatamodel[T](dm,(d:DatamodelData) => bind(d))
    }
  }
}
