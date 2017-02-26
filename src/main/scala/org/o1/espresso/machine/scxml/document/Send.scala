package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Send extends Raise with Executable {
  override def localName = "send"
  def transportType: DataValueExpression
  def event:DataValueExpression
  def target:DataValueExpression
  def id:DataValueExpression
  def delay:DataValueExpression
  def content:Content
  def params:List[Param] = Nil
}
