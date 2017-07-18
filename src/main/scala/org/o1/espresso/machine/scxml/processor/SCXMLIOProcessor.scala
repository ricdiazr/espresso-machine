package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.StateMachineProcess
import org.o1.espresso.machine.scxml.DocumentElementProcessor
import org.o1.espresso.machine.scxml.document.{Executable, Raise, Send}
import org.o1.logging.{Level, Logging}

import scala.concurrent.Future

/**
  * Created by ricardo on 1/29/17.
  * processes IOable elements between local and external SCXML Sessions [[SCXMLInstance]]S
  */
trait SCXMLIOProcessor extends DocumentElementProcessor[Executable,SCXMLEventInstance] with Logging {
  this: SCXMLEventListener with SCXMLEventDispatcher =>

  val externalIO:ExternalIO
  val registry:SCXMLInstanceRegistry

  /**
    * processes [[org.o1.espresso.machine.scxml.document.Executable]] objects that are meant to
    * create events
    * @param child any Executable
    * @param parent
    * @return
    */
  def process[E <: Executable](descendant: E,root:SCXMLInstance) = {
    descendant match {
      case s: Raise =>
      case s: Send => {
        dispatch(new Event(root,s.event.toString))
      }
      case _ => log(Level.Info, "executable element does not generate any event")
        None
    }
  }

  /**
    * sends the [[Event]] to the target [[SCXMLInstance]]
    * @param scxmle
    * @return returns a Future that will wait until the target session has been notified
    */
  override def dispatch(e:Event): Unit = {

  }

  override def notify(e:Event): Unit = {

  }
}
