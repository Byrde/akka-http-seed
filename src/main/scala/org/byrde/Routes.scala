package org.byrde

import org.byrde.configuration.Configuration
import org.byrde.controllers.directives.{ MarshallingEntityWithRequestAndAttrDirective, RequestResponseHandlingDirective }
import org.byrde.models.responses.CommonJsonServiceResponseDictionary.E0200

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.stream.ActorMaterializer
import akka.util.Timeout

trait Routes extends RequestResponseHandlingDirective with MarshallingEntityWithRequestAndAttrDirective {
  def configuration: Configuration

  implicit def system: ActorSystem

  implicit def materializer: ActorMaterializer

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
