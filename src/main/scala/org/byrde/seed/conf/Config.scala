package org.byrde.seed.conf

import org.byrde.akka.http.conf.{CORSConfig, ConfigLike}

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import akka.util.Timeout

import scala.concurrent.duration._

class Config @Inject() extends ConfigLike {
  lazy val config: com.typesafe.config.Config =
    ConfigFactory.load().resolve()

  lazy val underlyingAkkaConfiguration: com.typesafe.config.Config =
    config.getConfig("akka.server")

  lazy val name: String =
    underlyingAkkaConfiguration.getString("name")

  lazy val interface: String =
    underlyingAkkaConfiguration.getString("interface")

  lazy val port: Int =
    underlyingAkkaConfiguration.getInt("port")

  lazy val timeout: Timeout =
    Timeout(underlyingAkkaConfiguration.getInt("request-timeout").seconds)

  lazy val cors: CORSConfig =
    CORSConfig(config.getConfig("cors"))
}
