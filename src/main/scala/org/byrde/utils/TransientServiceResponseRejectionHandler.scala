package org.byrde.utils

import org.byrde.models.exceptions.TransientServiceResponseRejection

import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.RejectionHandler

object TransientServiceResponseRejectionHandler extends PlayJsonSupport {
  lazy val handler: RejectionHandler =
    RejectionHandler
      .newBuilder()
      .handle {
        case TransientServiceResponseRejection(ex) =>
          complete(ex.toJson)
      }
      .result()
}
