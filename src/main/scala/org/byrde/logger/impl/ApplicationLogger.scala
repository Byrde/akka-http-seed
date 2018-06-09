package org.byrde.logger.impl

import com.google.inject.Inject

import org.byrde.logger.Logger

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }

class ApplicationLogger @Inject() (actorSystem: ActorSystem) extends Logger {
  override protected def logger: LoggingAdapter =
    Logging(actorSystem, getClass)
}
