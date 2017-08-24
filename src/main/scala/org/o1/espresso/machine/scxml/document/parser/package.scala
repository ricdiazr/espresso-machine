package org.o1.espresso.machine.scxml.document

/**
  * Created by ricardo on 11/9/16.
  */
package object parser {

  case class ExecutableSeq(executables : Seq[ExecutableElement]) extends Exec
  implicit class GetAllExecs(execs:Seq[ExecutableSeq]) {
    def executables : Seq[ExecutableElement] = execs.map(_.executables).flatten
  }

}
