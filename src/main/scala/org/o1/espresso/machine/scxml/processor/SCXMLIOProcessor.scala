package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.StateMachineProcess
import org.o1.espresso.machine.scxml.{DocumentElement, DocumentElementProcessor, InvalidDocumentElementException}
import org.o1.espresso.machine.scxml.document.{Executable, IoType, Raise, Send}
import org.o1.logging.{Level, Logging}

/**
  * Created by ricardo on 1/29/17.
  * processes IOable elements between local and external SCXML Sessions [[SCXMLInstance]]S
  */
trait SCXMLIOProcessor[E <: DocumentElement] extends DocumentElementProcessor[E,SCXMLInstance] with Logging {
  this: SCXMLEventDispatcher =>
  val ioType: IoType.Value = IoType.SCXML
  val registry:SCXMLInstanceRegistry


  /**
    * processes [[org.o1.espresso.machine.scxml.document.Executable]] objects that are meant to
    * create events
    * @param descendant any Executable
    * @param root descendants God's
    * @return
    */
  def process[E <: Executable with StateMachineProcess](descendant: E,root:SCXMLInstance) = {

    descendant match {
      case s: Raise => dispatch(new Event(root,s.event.value.toString))
      //case s: Send => transport(IoType.withName(s.transportType.value.getOrElse("unknown"))(
       // Event(s,s.event.value)
      //)

      case _ => log(Level.Info, "executable element does not generate any event")
        None
    }
  }

  override def dispatch(e: Event): Unit = {
    val target:SCXMLInstance = e.target match {
      case Some(t) => registry.get(t).getOrElse(e.src.asInstanceOf[SCXMLInstance])
      case None => e.src.asInstanceOf[SCXMLInstance]
    }
  }

  def transport(ioType: IoType.Value) = (e:Event) => {
    ioType match {
      case IoType.SCXML => dispatch(e)
      case _ =>
          val src:Executable =e.src.asInstanceOf[Executable]
          throw InvalidDocumentElementException(
          src.xmlns, src.localName, "Unsupported transport type for this processor ")

    }
  }

}
