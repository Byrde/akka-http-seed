package org.byrde.controllers.v1

import org.byrde.commons.models.services.CommonsServiceResponseDictionary.E0200
import org.byrde.controllers.support.RouteSupport

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

class DefaultRoutes extends RouteSupport {
  lazy val routes: Route =
    ping

  def ping: Route =
    pathEnd {
      complete(E0200("pong"))
    }
}
