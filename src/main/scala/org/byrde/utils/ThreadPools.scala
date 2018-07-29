package org.byrde.utils

import java.util.concurrent.Executors

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor }

object ThreadPools {
  lazy val Global: ExecutionContextExecutor =
    ExecutionContext.global

  lazy val Services: ExecutionContextExecutor =
    ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}