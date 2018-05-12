package io.github.robhinds.akkops.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

trait Microservice {

  implicit lazy val system = ActorSystem()
  implicit lazy val materializer = ActorMaterializer()

  def route: Route

}
