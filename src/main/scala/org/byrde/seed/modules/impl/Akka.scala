package org.byrde.seed.modules.impl

import org.byrde.akka.http.conf.ConfigLike
import org.byrde.akka.http.modules.AkkaLike

import com.google.inject.Inject

import akka.actor.ActorSystem

class Akka @Inject() (config: ConfigLike) extends AkkaLike {
  override def system: ActorSystem =
    ActorSystem(config.name, config.config)
}
