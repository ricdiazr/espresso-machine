package org.o1.espresso.machine.scxml.processor

import org.o1.espresso.machine.scxml.document.{DataValueExpression, Datamodel, DatamodelData}

trait SCXMLDatamodel[T] {
  trait ECMAScript {
    val ct = "text/ecmascript"
  }

  protected lazy val datamodel:Map[String,T] = mapDatamodel
  protected def mapDatamodel:Map[String,T] = Map.empty

  def isLateBinding:Boolean

  def eval[V](expression:DataValueExpression, evaluator:(Map[String,T], String) => Option[V]): Option[V] =
    expression.value match {
      case Some(x) => evaluator(datamodel,x.toString)
      case _ => None
    }

  def merge[T](other:SCXMLDatamodel[T]):SCXMLDatamodel[T] = new SCXMLDatamodel[T] {
    override def isLateBinding:Boolean = isLateBinding
    override protected def mapDatamodel:Map[String,T] = mapDatamodel ++ other.mapDatamodel
  }

  protected[this] def bnd(list:List[DatamodelData],binder:(DatamodelData)=>T):Map[String,T] = {
    val m: Map[String, List[DatamodelData]] = list.groupBy(l => l.id)
    m.mapValues(v => {binder(v.head)})
  }
}

object SCXMLDatamodel {

  def apply[T] :SCXMLDatamodel[T] = new SCXMLDatamodel[T] {
    def isLateBinding:Boolean = false
  }

  def apply[T](dmMap:Map[String,T]): SCXMLDatamodel[T] = new SCXMLDatamodel[T] {
    def isLateBinding:Boolean = false
    override protected def mapDatamodel = dmMap
  }

  def apply[T](dm:Datamodel,b:(DatamodelData)=>T):SCXMLDatamodel[T] = apply(dm,b,Some(true))

  def apply[T](dm:Datamodel,b:(DatamodelData)=>T,lateFlag:Option[Boolean] = None):SCXMLDatamodel[T]
  = new SCXMLDatamodel[T] {
    //Poor man lazyness
    override def isLateBinding:Boolean = lateFlag.getOrElse(false)
    var isLazy = isLateBinding
    override protected def mapDatamodel:Map[String,T] = {
      println(s"isLazy binding ${isLazy}")
      if(isLazy) {
        isLazy = false
        Map.empty
      } else {
        bnd(dm.datas("*").toList,b)
      }}
  }
}