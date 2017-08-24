package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{DataValueExpression, Datamodel, DatamodelData}

trait SCXMLDatamodel[T] {

  val dataIndex:Map[String,T]

  def bnd(list:List[DatamodelData],binder:(DatamodelData)=>T):Map[String,T] = {
    val m: Map[String, List[DatamodelData]] = list.groupBy(l => l.id)
    m.mapValues(v => {binder(v.head)})
  }

  def eval[V](expression:DataValueExpression, evaluator:(Map[String,T], String) => Option[V]): Option[V] =
    expression.value match {
      case Some(x) => evaluator(dataIndex,x.toString)
      case _ => None
    }
}

object SCXMLDatamodel {
  def apply[T](dm:Datamodel,b:(DatamodelData)=>T) = new SCXMLDatamodel[T] {
    val dataIndex:Map[String,T] = bnd(dm.datas("*").toList,b)
  }
}