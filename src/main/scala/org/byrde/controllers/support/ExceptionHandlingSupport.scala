package org.byrde.controllers.support

import org.byrde.commons.models.services.CommonsServiceResponseDictionary.E0500
import org.byrde.commons.models.services.ServiceResponse
import org.byrde.commons.models.services.ServiceResponse.TransientServiceResponse
import org.byrde.commons.utils.JsonUtils
import org.byrde.commons.utils.exception.{ ClientException, ServiceResponseException }
import org.byrde.logger.impl.ErrorLogger

import play.api.libs.json.{ JsNull, JsSuccess, Json }
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpRequest, HttpResponse }
import akka.http.scaladsl.model.StatusCodes.MethodNotAllowed
import akka.http.scaladsl.model.headers.Allow
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ ExceptionHandler, MethodRejection, RejectionHandler }
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.util.Try

trait ExceptionHandlingSupport extends PlayJsonSupport with CORSSupport {
  def errorLogger: ErrorLogger

  private val ClientError =
    1

  implicit val handler: RejectionHandler =
    RejectionHandler
      .newBuilder()
      .handleAll[MethodRejection] { rejections =>
        lazy val methods =
          rejections.map(_.supported)

        lazy val names =
          methods.map(_.name).mkString(", ")

        cors {
          respondWithHeader(Allow(methods)) {
            options {
              complete(s"Supported methods : $names")
            }
          } ~
            complete(
              (MethodNotAllowed,
                s"HTTP method not allowed, supported methods: $names")
            )
        }
      }
      .result()
      .mapRejectionResponse {
        case res @ HttpResponse(_status, _, ent: HttpEntity.Strict, _) =>
          val status =
            _status.intValue

          val response =
            ent
              .data
              .utf8String

          //TODO: Very inefficient. We already have the response serialized to Json and ready to go, however we need to re-parse it to log the issue.
          Try(Json.parse(response))
            .toOption
            .getOrElse(JsNull)
            .validate[TransientServiceResponse[String]](ServiceResponse.reads(JsonUtils.Format.string("response"))) match {
              case JsSuccess(transientServiceResponse, _) =>
                errorLogger.error(ServiceResponseException(transientServiceResponse))

                res.copy(entity =
                  HttpEntity(
                    ContentTypes.`application/json`,
                    Json.stringify(transientServiceResponse.toJson)
                  ))

              case _ =>
                val clientException =
                  ClientException(normalizeString(response), ClientError, status)

                errorLogger.error(clientException)

                res.copy(entity =
                  HttpEntity(
                    ContentTypes.`application/json`,
                    Json.stringify(clientException.toJson)
                  ))
            }

        case res =>
          res
      }

  //Use ExceptionHandler for all server errors
  def exceptionHandler(req: HttpRequest): ExceptionHandler =
    ExceptionHandler {
      case exception: Throwable =>
        val serviceException =
          exception match {
            case serviceException: ServiceResponseException[_] =>
              errorLogger.error(serviceException, req)
              serviceException
            case _ =>
              errorLogger.error(exception, req)
              E0500(exception)
          }

        complete(Json.toJson(serviceException))
    }

  private def stripLeadingAndTrailingQuotes(value: String): String = {
    var tmp =
      value

    if (tmp.startsWith("\""))
      tmp = tmp.substring(1, tmp.length)

    if (value.endsWith("\""))
      tmp = tmp.substring(0, tmp.length - 1)

    tmp
  }

  private def removeNewLine(value: String): String =
    value.replaceAll("\n", "")

  private def normalizeString(value: String): String =
    removeNewLine(stripLeadingAndTrailingQuotes(value))
}
