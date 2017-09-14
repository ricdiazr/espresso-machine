package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.StateMachineProcess
import org.o1.espresso.machine.scxml.processor.dm.DataBinding

import scala.collection.mutable

/**
  * Created by ricardo on 12/20/16.
  */
trait SCXMLState extends StateMachineProcess
  with DataBinding
  with Transitionable
  with mutable.Publisher[Event] {
  def isAtomic:Boolean
  def isComposite:Boolean
  def isInitial:Boolean
  def isFinal:Boolean
  def isParallel:Boolean
}
