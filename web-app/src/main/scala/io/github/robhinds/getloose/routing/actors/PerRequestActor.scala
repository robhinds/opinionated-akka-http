package io.github.robhinds.getloose.routing.actors

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import io.github.robhinds.getloose.model.Actions.Action

import scala.concurrent.Promise
import scala.concurrent.duration._


trait PerRequestActor extends Actor {
  import context._

  def requestContext: RequestContext
  def routeResult: Promise[RouteResult]

  setReceiveTimeout(2.seconds)

  def complete(m: => ToResponseMarshallable): Unit = {
    val f = requestContext.complete(m)
    f.onComplete(routeResult.complete(_))
    stop(self)
  }

  override val supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy() {
      case _ =>
        complete(InternalServerError)
        Stop
    }
}
