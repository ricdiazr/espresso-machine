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

  test("The actual state machine consists of its children" +
  "datamodel=ecmascript") {
    info(s"number of states: ${doc.states.size}")
    assert(doc.datamodel.modelType == "ecmascript")
    info(s"number of states: ${doc.datamodel.toString}")
  }

}
