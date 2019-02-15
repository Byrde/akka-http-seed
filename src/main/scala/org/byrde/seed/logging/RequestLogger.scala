package org.byrde.seed.logging

import org.byrde.akka.http.logging.HttpRequestLogging
import org.byrde.akka.http.modules.AkkaLike
import org.byrde.logging.JsonLoggingFormat

import com.google.inject.Inject

import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.model.HttpRequest

import play.api.libs.json.{ JsString, Json }

class RequestLogger @Inject() (akka: AkkaLike) extends HttpRequestLogging {
  override lazy val logger: LoggingAdapter =
    Logging(akka.system, "request")

  def request(epoch: Long, status: String, req: HttpRequest)(implicit loggingInformation: JsonLoggingFormat[HttpRequest]): Unit = {
    val innerRequest =
      Json.obj(
        "status" -> JsString(status),
        "epoch" -> JsString(s"${epoch}ms"))

    logger.info((innerRequest ++ loggingInformation.format(req)).toString())
  }
}
