package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.DocumentElement


/**
  * Created by ricardo on 12/20/16.
  */
trait SCXMLProcessingStep {
  def isRunning = false

  def isDone = false

  private[processor] def eventLoop[A](body: => A, exp: => Boolean): Nothing = {
    if(exp) exp
    else body
    eventLoop(body, exp)
  }
}
