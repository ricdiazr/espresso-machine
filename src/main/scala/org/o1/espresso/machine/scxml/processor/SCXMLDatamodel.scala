package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{DataValueExpression, Datamodel, DatamodelData}

trait SCXMLDatamodel[T] {
  trait ECMAScript {
    val ct = "text/ecmascript"
  }

  protected[this] val dataIndex:Map[String,T] = Map.empty
  protected[this] def lateBinding = 0

  def eval[V](expression:DataValueExpression, evaluator:(Map[String,T], String) => Option[V]): Option[V] =
    expression.value match {
      case Some(x) => evaluator(dataIndex,x.toString)
      case _ => None
    }

  protected[this] def bnd(list:List[DatamodelData],binder:(DatamodelData)=>T):Map[String,T] = {
    val m: Map[String, List[DatamodelData]] = list.groupBy(l => l.id)
    m.mapValues(v => {binder(v.head)})
  }
}

object SCXMLDatamodel {
  def apply[T](dm:Datamodel,b:(DatamodelData)=>T):SCXMLDatamodel[T] = apply(dm,b,Some(true))
  def apply[T](dm:Datamodel,b:(DatamodelData)=>T,lateFlag:Option[Boolean] = None):SCXMLDatamodel[T]
  = new SCXMLDatamodel[T] {
    var isLazy = lateFlag.getOrElse(false)
    override val dataIndex:Map[String,T] = {
      if(!isLazy) {
        bnd(dm.datas("*").toList,b)
      } else {
        isLazy = false
        Map.empty
      }}
  }

}