package org.byrde.seed.guice

import org.byrde.akka.http.conf.ConfigLike
import org.byrde.akka.http.guice.AssistedInjectFactoryScalaModule
import org.byrde.akka.http.logging.{ HttpErrorLogging, HttpRequestLogging }
import org.byrde.akka.http.modules.AkkaLike
import org.byrde.seed.conf.Config
import org.byrde.seed.logging.{ ErrorLogger, RequestLogger }
import org.byrde.seed.modules.ModulesProvider
import org.byrde.seed.modules.impl.{ Akka, ConcreteModulesProvider }

import com.google.inject.{ AbstractModule, Binder }

import net.codingwell.scalaguice.ScalaModule

class ModuleBindings extends AbstractModule with ScalaModule with AssistedInjectFactoryScalaModule[Binder] {
  override def configure(): Unit = {
    bind[ConfigLike].to[Config].asEagerSingleton()
    bind[AkkaLike].to[Akka].asEagerSingleton()
    bind[HttpRequestLogging].to[RequestLogger].asEagerSingleton()
    bind[HttpErrorLogging].to[ErrorLogger].asEagerSingleton()
    bindFactory[ModulesProvider, ConcreteModulesProvider.Factory]()
  }
}
