package org.o1.espresso.machine

import scala.collection.mutable

trait StateMachineProcessRegistry[P <: StateMachineProcess] {
  val processes:mutable.Set[P] = mutable.LinkedHashSet();
  
  def register(p:P): StateMachineProcessRegistry.this.type = {
    processes += p
    StateMachineProcessRegistry.this
  }
  
  def unregister(p:P): StateMachineProcessRegistry.this.type = {
    processes -= p
    StateMachineProcessRegistry.this
  }
  
  def childrenOf(p:P): collection.Set[P]= {
    processes.filter(p.isMyChild)
  }
  
  def siblingsOf(p:P): collection.Set[P]= {
    processes.filter(p.isMySibling)
  }
  
  def parentOf(p:P):Option[P] = {
    processes.find(p.isMyParent)
  }

  def last: P = processes.last
}