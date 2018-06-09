package org.byrde.guice

import com.google.inject.Inject

import org.byrde.configuration.Configuration
import org.byrde.logger.impl.ApplicationLogger
import org.byrde.Modules

import akka.http.libs.typedmap.TypedKey
import akka.http.scaladsl.server.directives.HttpRequestWithEntity

case class ModulesProvider @Inject() (
    configuration: Configuration,
    akka: Akka,
    applicationLogger: ApplicationLogger
) {
  def ModulesAttr[T]: TypedKey[Modules[T]] =
    TypedKey[Modules[T]]("Modules")

  /**
   * Distinction is made between [[ModulesProvider]] & [[Modules]].
   * [[Modules]] is initialized and dependent on a request, this is useful
   * for initalizing and containing class members that will have cached assets that can't be
   * shared between requests. [[ModulesProvider]] is a subset of [[Modules]] where
   * the class members can be shared between requests, e.g injected members.
   *
   * @param req - The request to bind this instance of [[Modules]] to.
   * @tparam T - The request entity type
   * @return ModulesProvider with request applied to initialize class members dependent on the request
   */
  def apply[T](req: HttpRequestWithEntity[T]): Modules[T] =
    req.getAttr(ModulesAttr[T]).getOrElse(throw new Exception(s"Binding error `Modules` to request"))
}

