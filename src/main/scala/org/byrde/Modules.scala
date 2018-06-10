package org.byrde

import org.byrde.configuration.Configuration
import org.byrde.guice.{ Akka, ModulesProvider }
import org.byrde.logger.impl.ApplicationLogger

import akka.http.scaladsl.server.directives.HttpRequestWithEntity

class Modules[T](
  configuration: Configuration,
  akka: Akka,
  applicationLogger: ApplicationLogger
)(implicit requestWithEntity: HttpRequestWithEntity[T])

object Modules {
  def apply[T](modulesProvider: ModulesProvider)(implicit requestWithEntity: HttpRequestWithEntity[T]): Modules[T] =
    new Modules(
      modulesProvider.configuration,
      modulesProvider.akka,
      modulesProvider.applicationLogger
    )
}