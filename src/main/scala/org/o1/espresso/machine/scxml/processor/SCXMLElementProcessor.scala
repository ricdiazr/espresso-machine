package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.{ProcessStatus, StateMachineProcess}
import org.o1.espresso.machine.scxml.document.{ExecutableOn, _}
import org.o1.espresso.machine.scxml.DocumentElementProcessor
import org.o1.logging.Logging

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

trait SCXMLElementProcessor
  extends DocumentElementProcessor[SCXML,SCXMLSession] with Logging {

  val thisProcessor = this
  val registry: SCXMLInstanceRegistry
  val interpreter: SCXMLElementInterpreter
  val evaluator: SCXMLDataValueExpressionEvaluator
  val executablesHandler: SCXMLExecutableHandler[Executable]

  override def process(scxml: SCXML): SCXMLSession = {
    debug(s"process ${scxml.name}")
    interpret(scxml, registry.last match {
      case Some(p) => p.descriptor.processId + 1
      case _ => 1
    })
  }

  /**
    * The purpose of this procedure is to initialize the interpreter and to start processing.
    *
    * @param scxml
    * @param id
    * @return
    */
  def interpret(scxml: SCXML, id: Long): SCXMLSession = {
    registry.register(SCXMLInstance(scxml, id)).last match {
      case Some(s) => startup(s)
    }
  }

  protected[processor] def eventQueue(isExternal: Boolean = false): EventQueue


  /**
    * side effect function
    * At startup, the SCXML Processor MUST place the state machine in the configuration specified
    * by the 'initial' attribute of the <scxml> element.
    *
    * @param scxml Session registered at this processor's registry
    * @return scxml session which can be active or halt already
    */
  def startup(scxml: SCXMLInstance): SCXMLSession = {

    val p = Promise[SCXMLSession]()
    val f = p.future
    val producer = Future {
      val s = scxml.activate.asInstanceOf[SCXMLSession]
      p success s
    }

    val consumer = Future {
      //start doing something
      f onSuccess {
        case s => go(s)
      }
    }
    scxml.asInstanceOf[SCXMLSession]
  }

  def go(session: SCXMLSession): Unit = {
    val initial = interpreter.selectInitialState(session)
    RecursiveProcessingStep(
      macrostep(
        enter(SCXMLState(session.asInstanceOf[SCXMLInstance], initial, interpreter.datamodel(initial)))),
      (s: SCXMLState) => s.descriptor.status == ProcessStatus.Halt)
  }

  def transitionate(state: SCXMLState): SCXMLState = {
    val statesToEnter = Set()
    val statesForDefaultEntry = Set()
    // initialize the temporary table for default content in history states
    // defaultHistoryContent = new HashTable()
    //computeEntrySet(enabledTransitions, statesToEnter, statesForDefaultEntry, defaultHistoryContent)
    state
  }

  protected[this] def signal(target: SCXMLSession, status: ProcessStatus.Value, q: EventQueue): Unit = {
    q.enqueue(SignalEvent(target, status))
  }

  protected[this] def macrostep(configuration: SCXMLStateConfiguration[State]): SCXMLState

  protected[this] def microsteps(state: SCXMLState) =
    RecursiveProcessingStep(transitionate(state))

  protected[this] def enter(state: SCXMLState, entryCount: Int = 0): SCXMLStateConfiguration[State] = {
    val activeState: State = state.configuration.states.last
    val enabledTransitions: Seq[NodeElement[Transition]] = selectEnabledTransitions(state).map(activeState.newFamily)
    val rootCtx: SCXMLDatamodel[_] = executeContent(enabledTransitions, state.datamodel)
    val statesToEnter: Seq[NodeElement[State]] = enabledTransitions.flatMap(interpreter.computeEntrySet)

    def executeStateContent(ctx: SCXMLDatamodel[_], s: NodeElement[State], boundOn: ExecutableOn.Value): SCXMLDatamodel[_] = {
      val onentry: Seq[NodeElement[Executable]] = s().executables(
        Some(boundOn)).getOrElse(Nil).map(activeState.newFamily)
      executeContent(onentry, ctx)
    }

    statesToEnter.foldLeft(state)((p, stateElement) => for {
      oentrycontent: SCXMLDatamodel[_] <- p.datamodel.merge(tranctx).merge(executeStateContent(interpreter.datamodel(stateElement()), stateElement, ExecutableOn.Entry))
      defaultcontent: SCXMLDatamodel[_] <-
      if (stateElement().stateType == StateType.Compound)
        oentrycontent.merge(executeContent(
          interpreter.selectInitialTransitions(stateElement(), 1).map((t) => stateElement().newFamily(t)).asInstanceOf[Seq[NodeElement[_]]],
          oentrycontent))
      else oentrycontent

    } yield SCXMLState(stateElement(),
      (entryCount + 1 + state.descriptor.processId * 32 * stateElement().hashCode()), state, Some(defaultcontent))
    ).configuration
  }

  protected def selectEnabledTransitions(state: SCXMLState, evtOpt: Option[Event] = None): Seq[Transition] = {
    evtOpt match {
      case Some(e) => Nil //calculates based on context eval(state.datamodel, new StringValue(e.descriptor))//
      case None => interpreter.selectInitialTransitions(state.configuration.state,1)
    }
  }

  protected def executeContent(contentHolders:Seq[NodeElement[_]],context:SCXMLDatamodel[_]): SCXMLDatamodel[_] = {
    contentHolders.foldLeft(context)((ctx,holderNode)=> {
      holderNode.children.foldLeft(ctx)((ctx,exec) =>executablesHandler.execute(ctx,exec.asInstanceOf,(e)=>true))})
  }

  protected def eval[V](dm: SCXMLDatamodel[_], xp: DataValueExpression): Option[V] = {
    None
  }

}
object RecursiveProcessingStep {
  def apply[T](body: => T, exp:(T) => Boolean = (T)=>true): T = {
    @tailrec
    def recurse(body: => T):T = {
      val r:T = body
      if(exp(r)) r else recurse(body)
    }
    recurse(body)
  }
}