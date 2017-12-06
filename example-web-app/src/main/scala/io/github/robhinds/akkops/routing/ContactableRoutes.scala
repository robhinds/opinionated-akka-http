package io.github.robhinds.akkops.routing

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.github.robhinds.akkops.model.core.Actions.GetUserRequest
import io.github.robhinds.akkops.routing.directives.Directives._
import io.github.robhinds.akkops.services.ContactableServiceComponent
import ResponseHandler._
import io.circe.generic.auto._

class ContactableRoutes {
  this: ContactableServiceComponent =>

  val routes: Route =
    getPath("users" / Segment) { uuid =>
      respond(contactableService.getContactable(GetUserRequest(None, Some(uuid))))
    } ~
    getPath("users" / Segment) { handle =>
      respond(contactableService.getContactable(GetUserRequest(Some(handle), None)))
    }

}
