package org.byrde.logger.impl

import com.google.inject.Inject

import org.byrde.logger.{ Logger, LoggingInformation }
import org.byrde.services.{ NameForLogging, ServiceRequestResponse }

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.directives.HttpRequestWithEntity
import akka.stream.Materializer

import scala.concurrent.{ ExecutionContext, Future }

class ServiceLogger @Inject() (actorSystem: ActorSystem) extends Logger {
  override protected val logger: LoggingAdapter =
    Logging(actorSystem, getClass)

  def logService[T](
    service: NameForLogging,
    serviceRequest: HttpRequestWithEntity[T],
    originalRequest: HttpRequestWithEntity[_]
  )(fn: HttpRequestWithEntity[T] => Future[HttpResponse])(implicit materializer: Materializer, ec: ExecutionContext, loggingInformation: LoggingInformation[ServiceRequestResponse[T]]): Future[HttpResponse] = {
    val start: Long = System.currentTimeMillis()

    fn(serviceRequest).map { serviceResponse =>
      serviceRequest.request.discardEntityBytes()

      info(
        ServiceRequestResponse(
          service,
          System.currentTimeMillis() - start,
          originalRequest,
          serviceRequest,
          serviceResponse
        )
      )

      serviceResponse
    }
  }
}