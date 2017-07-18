package org.o1.espresso.machine.scxml.processor

/**
  * Created by ricardo on 12/20/16.
  */
trait EventQueue {
  def enqueue(e:Event):Unit
  def dequeue():Event
  def isEmpty = {true}
}
