package io.github.robhinds.getloose.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.circe.Decoder
import io.github.robhinds.getloose.routing.directives.Directives._
import io.github.robhinds.getloose.model.Actions.GetUserRequest
import io.github.robhinds.getloose.services.ContactableServiceComponent

class ContactableRoutes {
  this: ContactableServiceComponent =>

  val routes: Route =
    getPath("users" / JavaUUID) { uuid =>
      complete(contactableService.getContactable(GetUserRequest(None, Some(uuid))))
    } ~
    getPath("users" / String) { handle =>
      complete(contactableService.getContactable(GetUserRequest(Some(handle), None)))
    }

}

sealed trait ErrorResponse
case class NotFound(private val statusCode: StatusCode = StatusCodes.NotFound)

abstract class Controller[A: Decoder] {
  import io.circe.syntax._
  import io.circe.generic.auto._
  def handleRequest(response: Either[ErrorResponse, A]) = {
    response match {
      case Left(e) => complete(e.asJson.toString)
      case Right(a) => complete(a.asJson.toString)
    }
  }
}
