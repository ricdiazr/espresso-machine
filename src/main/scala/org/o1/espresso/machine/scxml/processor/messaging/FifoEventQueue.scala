package org.o1.espresso.machine.scxml.processor.messaging

import org.o1.espresso.machine.scxml.processor.{Event, EventQueue}

import scala.collection.mutable.{Queue}

trait FifoEventQueue extends EventQueue {
  val queue:Queue[Event]

  def enqueue(e:Event):Unit = queue.enqueue(e)

  def dequeue():Event = queue.dequeue()

  override def isEmpty = queue.isEmpty
}
object FifoEventQueue {
  def apply(): EventQueue = new FifoEventQueue {
    val queue:Queue[Event] = new Queue[Event]()
  }
}
