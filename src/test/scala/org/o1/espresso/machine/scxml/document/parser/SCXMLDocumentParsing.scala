import org.o1.espresso.machine.scxml.document.{Datamodel, State}
import org.o1.espresso.machine.scxml.document.parser._
import org.scalatest._

class SCXMLDocumentParsing extends FunSuite with Matchers {

  lazy val  doc = SCXMLDocument(getClass.getResource("/Main.scxml").toString)

  test("load a SCXML document by URL") {
    assert(!doc.name.isEmpty)
  }

  test("scxml should support version=1.0") {
    assert(doc.version == "1.0")
  }

  test("The id of the initial state(s) for the document." +
    "If not specified, the default initial state is the first child state in document order.") {
    val id = doc.initial.getOrElse(doc.states.head.id.getOrElse(""))
    info(s"initial state id: ${id}")
    assert(doc.states.exists(_.id.exists(_==id)))
  }

  test("The actual state machine consists of its children") {
    info("<state> A compound or atomic state. Occurs zero or more times.")
    assert(doc.states.size >= 0)
    info("<datamodel datamodel=ecmascript>  Defines part or all of the data model. Occurs 0 or 1 times.")
    assert(doc.datamodel.modelType == "ecmascript")
    info("<final> A top-level final state in the state machine. Occurs zero or more times")
    assert(doc.finals.size >= 0)
    info("<script> Provides scripting capability occurs")
    doc.script match {
      case Some(x) => info("once")
      case None  => info("once or none")}
  }

}
