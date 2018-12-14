package org.byrde.seed

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object ThreadPools {
  lazy val Global: ExecutionContextExecutor =
    ExecutionContext.global
}
