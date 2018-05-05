package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import io.github.robhinds.akkops.model.core.Errors._

trait DefaultErrorHandler extends ErrorHandler {
  override def errorStatusCode(er: ErrorResponse): StatusCode = er match {
    case x: NotFound =>  StatusCodes.NotFound
    case x: BadRequest => StatusCodes.BadRequest
    case x: Unauthorized =>  StatusCodes.Unauthorized
    case _ => StatusCodes.InternalServerError
  }
}
