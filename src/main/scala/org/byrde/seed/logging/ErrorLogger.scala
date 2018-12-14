package org.byrde.seed.logging

import org.byrde.akka.http.logging.HttpErrorLogging
import org.byrde.logging.JsonLoggingFormat
import org.byrde.seed.modules.impl.Akka

import com.google.inject.Inject

import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.model.HttpRequest

class ErrorLogger @Inject() (akka: Akka) extends HttpErrorLogging {
  override val logger: LoggingAdapter =
    Logging(akka.system, "error")

  def error[T](elem: HttpRequest, throwable: Throwable)(implicit loggingInformation: JsonLoggingFormat[(HttpRequest, Throwable)]): Unit =
    logger.error(loggingInformation.format(throwable.getMessage, elem -> throwable).toString)
}
