package org.byrde.seed.modules.impl

import org.byrde.akka.http.logging.{ HttpErrorLogging, HttpRequestLogging }
import org.byrde.akka.http.modules.AkkaLike
import org.byrde.seed.conf.Config
import org.byrde.seed.modules.ModulesProvider
import org.byrde.seed.Codes

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted

class ConcreteModulesProvider @Inject() (
    @Assisted() classLoader: ClassLoader,
    val config: Config,
    val akka: AkkaLike,
    val RequestLogger: HttpRequestLogging,
    val ErrorLogger: HttpErrorLogging
) extends ModulesProvider {
  lazy val SuccessCode =
    Codes.Success

  lazy val ErrorCode =
    Codes.Error
}

object ConcreteModulesProvider {
  trait Factory {
    def withClassLoader(classLoader: ClassLoader): ConcreteModulesProvider
  }
}
