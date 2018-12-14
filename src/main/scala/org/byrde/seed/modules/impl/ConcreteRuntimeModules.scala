package org.byrde.seed.modules.impl

import org.byrde.akka.http.modules.RuntimeModulesLike
import org.byrde.akka.http.scaladsl.server.directives.HttpRequestWithEntity
import org.byrde.seed.modules.{ ModulesProvider, RuntimeModules }

class ConcreteRuntimeModules[Req] extends RuntimeModules[Req]

object ConcreteRuntimeModules extends RuntimeModulesLike.RuntimeModulesBuilderLike[RuntimeModules, ModulesProvider] {
  override def apply[Req](provider: ModulesProvider)(implicit req: HttpRequestWithEntity[Req]): ConcreteRuntimeModules[Req] =
    new ConcreteRuntimeModules[Req] {}
}