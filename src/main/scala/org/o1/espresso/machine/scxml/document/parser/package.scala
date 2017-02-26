package org.o1.espresso.machine.scxml.document

import scala.xml.Node

/**
  * Created by ricardo on 11/9/16.
  */
package object parser {

  trait Exec {
    def executables: Seq[ExecutableElement]
  }
  case class ExecutableSeq(executables : Seq[ExecutableElement]) extends Exec
  implicit class GetAllExecs(execs:Seq[ExecutableSeq]) {
    def executables : Seq[ExecutableElement] = execs.map(_.executables).flatten
  }

  object DataValueExpression {
    def apply(node: Node,valueAttr:String, exsuffix:String =  "expr") = new DataValueExpression(
      if( (node \@ valueAttr).isEmpty ) None else Some(node \@ valueAttr),
      if( (node \@ valueAttr).isEmpty ) Some(node \@ valueAttr + exsuffix) else None
      )
  }

}
