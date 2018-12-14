package org.byrde.seed

import org.byrde.akka.http.RouteLike
import org.byrde.seed.modules.{ ModulesProvider, RuntimeModules }

import akka.http.scaladsl.server.RejectionHandler

trait ServerLike extends org.byrde.akka.http.ServerLike[RuntimeModules, ModulesProvider] {
  override implicit def provider: ModulesProvider

  lazy val handlers: Set[RejectionHandler] =
    Set.empty[RejectionHandler]

  lazy val v1: Map[Domain, RouteLike] =
    Map()

  lazy val map: Map[Version, Map[Domain, RouteLike]] =
    Map("v1" -> v1)
}
