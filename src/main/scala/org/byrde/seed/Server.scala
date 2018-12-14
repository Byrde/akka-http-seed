package org.byrde.seed

import org.byrde.seed.guice.ModuleBindings
import org.byrde.seed.modules.impl.{ ConcreteModulesProvider, ConcreteRuntimeModules }

import com.google.inject.{ Guice, Injector }

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

object Server extends App with ServerLike {
  import net.codingwell.scalaguice.InjectorExtensions._

  private val injector: Injector =
    Guice.createInjector(new ModuleBindings())

  override implicit def global: ExecutionContextExecutor =
    ThreadPools.Global

  implicit lazy val provider: ConcreteModulesProvider =
    injector
      .instance[ConcreteModulesProvider.Factory]
      .withClassLoader(getClass.getClassLoader)

  lazy val builder: ConcreteRuntimeModules.type =
    ConcreteRuntimeModules

  private implicit val materializer: ActorMaterializer =
    ActorMaterializer()

  Http()
    .bindAndHandle(
      routes,
      provider.config.interface,
      provider.config.port
    )
}
