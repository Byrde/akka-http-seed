package org.byrde

import com.google.inject.{ Guice, Injector }
import org.byrde.configuration.Configuration
import org.byrde.guice.ModulesProvider
import org.byrde.guice.modules.ModuleBindings
import org.byrde.logger.impl.{ ErrorLogger, RequestLogger }
import org.byrde.utils.ThreadPools

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.ExecutionContext

object Server extends App with Routes {
  import net.codingwell.scalaguice.InjectorExtensions._

  private val injector: Injector =
    Guice.createInjector(new ModuleBindings())

  override lazy val modulesProvider: ModulesProvider =
    injector.instance[ModulesProvider]

  override lazy val requestLogger =
    injector.instance[RequestLogger]

  override lazy val errorLogger =
    injector.instance[ErrorLogger]

  lazy val configuration: Configuration =
    modulesProvider.configuration

  override lazy val corsConfiguration =
    modulesProvider.configuration.corsConfiguration

  implicit val ec: ExecutionContext =
    ThreadPools.Global

  implicit lazy val system: ActorSystem =
    modulesProvider.akka.system

  implicit lazy val timeout: Timeout =
    configuration.timeout

  implicit lazy val materializer: ActorMaterializer =
    ActorMaterializer()

  Http()
    .bindAndHandle(
      routes,
      configuration.interface,
      configuration.port
    )
}