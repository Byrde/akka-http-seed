package org.byrde.seed

import org.byrde.seed.modules.{ModulesProvider, RuntimeModules}

import akka.http.scaladsl.server.{RejectionHandler, Route}

trait ServerLike extends org.byrde.akka.http.ServerLike[RuntimeModules, ModulesProvider] {
  override implicit def provider: ModulesProvider

  lazy val handlers: Set[RejectionHandler] =
    Set.empty[RejectionHandler]

  lazy val routes: Route =
    ???
}
