package org.byrde.controllers

import org.byrde.controllers.directives.MarshallingEntityWithRequestAndAttrDirective
import org.byrde.controllers.support.{ RequestResponseHandlingSupport, RouteSupport }
import org.byrde.guice.ModulesProvider

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout

import scala.concurrent.ExecutionContext

trait Routes extends RouteSupport with RequestResponseHandlingSupport with MarshallingEntityWithRequestAndAttrDirective {
  def modulesProvider: ModulesProvider

  implicit def ec: ExecutionContext

  implicit def system: ActorSystem

  implicit def timeout: Timeout

  lazy val versionedRoutes: Route =
    pathPrefix("v1") {
      reduceRoutes(version1)
    }

  lazy val version1 =
    Map(
      "ping" -> new v1.DefaultRoutes().routes
    )

  lazy val routes: Route =
    requestResponseHandler {
      versionedRoutes
    }

  private def reduceRoutes(pathBindings: Map[String, Route]): Route =
    pathBindings.map {
      case (k, v) => pathPrefix(k)(v)
    } reduce (_ ~ _)
}
