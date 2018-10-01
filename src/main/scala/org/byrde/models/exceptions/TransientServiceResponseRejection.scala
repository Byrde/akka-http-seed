package org.byrde.models.exceptions

import org.byrde.commons.utils.exception.ServiceResponseException.TransientServiceResponseException

import akka.http.scaladsl.server.Rejection

final case class TransientServiceResponseRejection(ex: TransientServiceResponseException)
  extends Rejection