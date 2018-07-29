package org.byrde

import org.byrde.commons.models.services.CommonsServiceResponseDictionary.E0200
import org.byrde.controllers.directives.MarshallingEntityWithRequestAndAttrDirective
import org.byrde.controllers.support.{ RequestResponseHandlingSupport, RouteSupport }
import org.byrde.guice.ModulesProvider

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.util.Timeout

import scala.concurrent.ExecutionContext

trait Routes extends RouteSupport with RequestResponseHandlingSupport with MarshallingEntityWithRequestAndAttrDirective {
  def modulesProvider: ModulesProvider

  implicit def ec: ExecutionContext

  implicit def system: ActorSystem

  implicit def timeout: Timeout

  lazy val defaultRoutes: Route =
    path("ping") {
      complete(E0200("pong"))
    }

  lazy val pathBindings =
    Map(
      "api" -> defaultRoutes
    )

  lazy val routes: Route =
    requestResponseHandler {
      pathBindings.map {
        case (k, v) => path(k)(v)
      } reduce (_ ~ _)
    }
}
