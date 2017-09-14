package org.o1.espresso.machine

import scala.collection.concurrent.TrieMap
import scala.collection.mutable.Map

trait StateMachineProcessRegistry[P <: StateMachineProcess] {
  protected[this] val processes:Map[Long,P] = TrieMap()
  private[this] var lastResgistered:Option[Long] = None

  def register(p:P): StateMachineProcessRegistry.this.type = {
    if(processes.contains(p.descriptor.processId))
      new IllegalStateException("process already registered")
    processes.put(p.descriptor.processId,p)
    synchronized {
      lastResgistered = Some(p.descriptor.processId)
    }
    StateMachineProcessRegistry.this
  }
  
  def unregister(p:P): StateMachineProcessRegistry.this.type = {
    processes.remove(p.descriptor.processId)
    StateMachineProcessRegistry.this
  }
  
  def childrenOf(p:P): collection.Set[P]= {
    processes.values.filter(p.isMyChild).toSet
  }
  
  def siblingsOf(p:P): collection.Set[P]= {
    processes.values.filter(p.isMySibling).toSet
  }
  
  def parentOf(p:P):Option[P] = {
    processes.find((t) => p.isMyParent(t._2)) match {case Some(t) => Some(t._2)}
  }

  def last: Option[P] = for {
    k <- lastResgistered
    p <- processes.get(k)
  } yield p

}