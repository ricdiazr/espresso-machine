import org.o1.espresso.machine.scxml.document.{Datamodel, ExecutableOn, State, StateType}
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
      case Some(x) => info(s"once ${x}")
      case None  => info("once or none")}
  }

  test("<state> Holds the representation of a state") {
    info("<state> A compound or atomic state. Occurs zero or more times.")
    val state:State = doc.states.head
    state.id match {
      case Some(s) => info(s"The identifier for this state. ${s}")
      case None => info(s"none, building an identifier is delegated to the processor")
    }
    val initial = state.initial.headOption
    initial match {
      case Some(x) => info(s"${x}: MUST NOT be specified in conjunction with the <initial> element.${state}")
        assert(state.states.filter( _.stateType == StateType.Initial).size == 0)
      case _=> assert(state.states.filter( _.stateType == StateType.Initial).size > 0)
    }
    val printinfo = (s:State) =>{
      info(s"<onentry> holds executable content at any level ${s.id}")
      s.executables(Some(ExecutableOn.Entry)) match {
        case Some(x) => info(s"<onentry> Occurs 0 or more times content at any level ${x.size}")
        case None => info(s"<onentry> Occurs 0 or more times")
      }
      s.executables(Some(ExecutableOn.Exit)) match {
        case Some(x) => info(s"<onexit> Occurs 0 or more times content at any level ${x.size}")
        case None => info(s"<onexit> Occurs 0 or more times")
      }
      info(s"<transition> Defines an outgoing transition from this state. Occurs 0 or more times. ${s.transitions.size}")
      info(s"<invoke>  Invokes an external service. Occurs 0 or more times. ${s.invokes.size}")
      info(s"<finals>  Defines a final substate. Occurs 0 or more times. ${s.finals.size}")
    }
    state.states.foreach((s)=>{
      printinfo(s)
      s.states.foreach(printinfo)
    })
  }
}
