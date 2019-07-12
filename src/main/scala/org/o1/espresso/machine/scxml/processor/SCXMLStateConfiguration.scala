package org.o1.espresso.machine.scxml
package processor
import org.o1.espresso.machine.scxml.document.State

import scala.annotation.tailrec

class SCXMLStateConfiguration[S <: State](val parent:Option[SCXMLStateConfiguration[S]],
                                          val instanceId:String,
                                          val state:S) {

  val thisInstance:SCXMLStateConfiguration[S] = this

  def hasParent = parent match {
    case None => false
    case _ => true
  }

  val states : Seq[S] = buildSatesSeq(state)

  def ++[S] (child:S): SCXMLStateConfiguration[S] = new SCXMLStateConfiguration[S](
    Some(thisInstance).asInstanceOf,
    thisInstance.instanceId,
    child)

  private[this] def buildSatesSeq[S](s:S):Seq[S] = {
    @tailrec
    def recurse(result: Seq[S]): Seq[S] = {
      parent match {
        case Some(p) => recurse(result :+ p.asInstanceOf)
        case None => result
      }
    }
    recurse(Vector[S](s))
  }

}
