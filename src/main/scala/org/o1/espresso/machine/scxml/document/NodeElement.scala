package org.o1.espresso.machine.scxml.document

import org.o1.espresso.machine.scxml.DocumentElement

/**
  * finite double linked kind of structure for querying:
  * 1. who is my parent
  * 2. who are my children
  * @tparam T
  */
trait NodeElement[T <: DocumentElement] {
  val thisInstance:NodeElement[T] = this
  val _Elm:T
  def apply()= _Elm
  def parent:Option[NodeElement[_]] = None
  def children:Seq[NodeElement[_]] = Seq[NodeElement[_]]()

  def newFamily[T](kid:T) = NodeElement[T](thisInstance,kid)
  def ID:Option[String] = None
}

object NodeElement {

  def apply[T <: DocumentElement](e:T) = new NodeElement[T] {
    override val _Elm:T = e
  }

  def apply[T <: DocumentElement](p:NodeElement[_], e:T) = new NodeElement[T] {
    override val _Elm:T = e
    override def parent = Some(p)
  }

  def apply[T <: DocumentElement](p:T, c:T*) = new NodeElement[T] {
    override val _Elm:T = p
    override def children:Seq[NodeElement[_]] = c.map(newFamily)
  }
}
