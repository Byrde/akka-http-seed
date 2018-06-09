package org.byrde.models.exceptions

import org.byrde.models.DefaultJsonServiceResponse

import scala.util.control.NoStackTrace

case class JsonServiceResponseException(msg: String, code: Int, status: Int) extends Throwable(msg) with DefaultJsonServiceResponse with NoStackTrace {
  override def apply(message: String): JsonServiceResponseException =
    new JsonServiceResponseException(message, code, status)

  def apply(throwable: Throwable): JsonServiceResponseException =
    apply(new Exception(throwable))

  def apply(exception: Exception): JsonServiceResponseException =
    new JsonServiceResponseException(exception.getMessage, code, status)
}

object JsonServiceResponseException {
  object E0400 extends JsonServiceResponseException("Bad Request", 400, 400)
  object E0500 extends JsonServiceResponseException("Internal Server Error", 500, 500)

  def apply(throwable: Throwable): JsonServiceResponseException =
    apply(throwable)

  def apply(ex: Exception): JsonServiceResponseException =
    E0500.copy(msg = ex.getMessage)
}
