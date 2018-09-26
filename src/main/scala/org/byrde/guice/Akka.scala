package org.byrde.guice

import org.byrde.configuration.Configuration

import com.google.inject.Inject

import akka.actor.ActorSystem

class Akka @Inject() (configuration: Configuration) {
  val system: ActorSystem =
    ActorSystem(configuration.name, configuration.underlyingConfig)
}
