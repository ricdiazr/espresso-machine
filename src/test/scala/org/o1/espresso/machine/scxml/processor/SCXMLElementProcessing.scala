package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.ProcessStatus
import org.o1.espresso.machine.scxml.document.SCXML
import org.o1.espresso.machine.scxml.document.parser.SCXMLDocument
import org.o1.espresso.machine.scxml.processor.dm.DataBinding
import org.o1.espresso.machine.scxml.processor.messaging.FifoEventQueue
import org.o1.logging.slj4.SLF4JLogging
import org.scalatest._

class SCXMLElementProcessing extends FunSuite with Matchers {
  lazy val maindoc = SCXMLDocument(getClass.getResource("/Main.scxml").toString)
  lazy val processor:SCXMLElementProcessor = new SCXMLElementProcessor  with SLF4JLogging {
    override val registry = new  SCXMLInstanceRegistry with SLF4JLogging
    override val interpreter: SCXMLElementInterpreter = new SCXMLElementInterpreter with SLF4JLogging with DataBinding
    override protected[processor] def eventQueue(isExternal: Boolean): EventQueue = FifoEventQueue()
  }
  lazy val mainscxml:SCXMLInstance with SCXML= processor.process(maindoc)

  test("creates and registers a brand new SCXMLInstance for processing") {

    info(s"${mainscxml.name}.${mainscxml.instanceId} - status:${mainscxml.descriptor.status}")
    assert(processor.registry.last match {
      case Some(i) => i.instanceId == mainscxml.instanceId
      case _=>false
    })

  }

  test("returned SCXMLInstance is an ACTIVE StateMachineProcess") {
    info(s"${mainscxml.name}.${mainscxml.instanceId} - status:${mainscxml.descriptor.status}")
    assert(processor.registry(mainscxml.instanceId) match {
      case Some(i) => i.descriptor.status == ProcessStatus.Active
      case _=>false
    })
  }

  test("supports multiple SCXMLInstanceS of a single SXCML") {
    val mainscxml2 = processor.process(maindoc)
    info(s"${mainscxml2.name}.${mainscxml2.instanceId}")

    assert(processor.registry.last match {
      case Some(i) => i.instanceId != mainscxml.instanceId
      case _=>false
    })
  }

}
