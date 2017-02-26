package org.o1.espresso.machine

import org.joda.time.Instant

/**
  * Created by ricardo on 12/20/16.
  */
case class ProcessDescriptor(val processId:Long,
                             val parentProcessId:Long,
                             val status:ProcessStatus.Value,
                             val start:Instant = Instant.now)
