package org.o1.espresso.machine.scxml

import org.o1.espresso.machine.scxml.document.SCXML


package object processor {
  type SCXMLSession = SCXMLInstance with SCXML
  object Error extends Enumeration {
    val execution, communication = Value
  }
}
