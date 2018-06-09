package org.byrde

import org.byrde.configuration.Configuration
import org.byrde.guice.Akka
import org.byrde.logger.impl.ApplicationLogger

import akka.http.scaladsl.server.directives.HttpRequestWithEntity

case class Modules[T](
  configuration: Configuration,
  akka: Akka,
  applicationLogger: ApplicationLogger
)(implicit requestWithNoEntity: HttpRequestWithEntity[T])