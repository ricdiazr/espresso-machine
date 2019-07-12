package org.o1.espresso.machine.dsl

trait StateMachine {

  var states = Seq.empty[State]
  var finalStates = Seq.empty[State]
  var currentState : State = null
  var input:String=""
  val name : Option[String]
  val transition = new Transition
  val transitionMap = transition.transitionMap

  object StateMachine {

    def newStateMachine( f: StateMachine => Unit,name:String ) :new StateMachine {
      val dfa = new StateMachine(name)
      f(dfa)
      dfa
    }

  }
}
