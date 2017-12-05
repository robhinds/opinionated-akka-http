package io.github.robhinds.getloose.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.circe.Decoder
import io.github.robhinds.getloose.routing.directives.Directives._
import io.github.robhinds.getloose.model.Actions.GetUserRequest
import io.github.robhinds.getloose.services.ContactableServiceComponent

class ContactableRoutes extends Controller {
  this: ContactableServiceComponent =>

  val routes: Route =
    getPath("users" / Segment) { uuid =>
      handleRequest(contactableService.getContactable(GetUserRequest(None, Some(uuid))))
    } ~
    getPath("users" / Segment) { handle =>
      handleRequest(contactableService.getContactable(GetUserRequest(Some(handle), None)))
    }

}

sealed trait ErrorResponse
case object NotFound{
  val statusCode: StatusCode = StatusCodes.NotFound
}

trait Controller {
  import io.circe.syntax._
  import io.circe.generic.auto._
  def handleRequest[A: Decoder](response: Option[A]) = {
    response match {
      case Some(x) => complete(x.asJson.toString)
      case None => complete(NotFound.asJson.toString)
    }
  }
}
