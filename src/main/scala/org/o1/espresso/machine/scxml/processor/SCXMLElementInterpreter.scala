package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml._
import org.o1.espresso.machine.scxml.document.{SCXML, State}

/**
  * Created by ricardo on 11/11/16.
  */
trait SCXMLElementInterpreter {

  def interpret(scxml:SCXML) = {
    lazy val validate:Boolean = isDocumentValid(scxml)
    if(validate) (scxml, scxml.datamodel)
    else throw NonConformantSCXMLException(scxml.localName, "does not define a least one valid state")
  }

  def isDocumentValid(scxml:SCXML):Boolean = {
    if (scxml.states.size >= 1) true
    else if (scxml.finals.size >= 1 ) true
    else scxml.initial match {
      case Some(i) => scxml.states.count((s:State)=> s.id == i) == 1
      case _ => false
    }
  }


}
