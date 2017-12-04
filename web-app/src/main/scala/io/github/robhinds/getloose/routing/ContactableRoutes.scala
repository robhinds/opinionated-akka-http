package io.github.robhinds.getloose.routing

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.github.robhinds.getloose.model.Actions.GetUserRequest
import io.github.robhinds.getloose.routing.actors.PerRequestActorFactory
import io.github.robhinds.getloose.services.ContactableServiceComponent

class ContactableRoutes extends PerRequestActorFactory{
  this: ContactableServiceComponent =>

  val routes: Route =
    path("users" ) {
      path(JavaUUID) { uuid =>
        pathEndOrSingleSlash {
          get {
            complete(contactableService.getContactable(GetUserRequest(None, Some(uuid))))
          }
        }
      }
    }

}
