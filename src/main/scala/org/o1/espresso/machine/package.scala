package org.o1.espresso

import org.joda.time.{Instant, Duration};
import java.util.UUID;

package object machine {

  object ProcessStatus extends Enumeration {
    val Idle, Active, Waiting, Halt = Value
  }
}