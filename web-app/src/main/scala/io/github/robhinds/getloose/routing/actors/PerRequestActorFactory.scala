package io.github.robhinds.getloose.routing.actors

import java.util.UUID

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import io.github.robhinds.getloose.model.Actions.Action

import scala.concurrent.Promise

trait PerRequestActorFactory {
  def actorSys: ActorSystem

  def complete(targetProps: Props, request: Action)(implicit ac: ActorSystem): Route = ctx => {
    //extract identity here and pass to actor
    val p = Promise[RouteResult]
    val perRequestActor = ac.actorOf(Props(classOf[PerRequestActor], ctx, p), s"REQUEST-${UUID.randomUUID()}")
    perRequestActor ! request
    p.future
  }
}