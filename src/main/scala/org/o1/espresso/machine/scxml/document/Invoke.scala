package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 12/20/16.
  */
trait Invoke extends Executable {
  def localName = "invoke"
  def invokingType:DataValueExpression
  def id:DataValueExpression
  def src:DataValueExpression
  def finalise:Seq[Executable] = Nil
  def autoforward=false
  def params:List[Param] = Nil
  def content:Option[Content] = None
}
