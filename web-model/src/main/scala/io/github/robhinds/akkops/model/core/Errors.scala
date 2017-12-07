package io.github.robhinds.akkops.model.core

import akka.http.scaladsl.model.StatusCodes

object Errors {

  sealed trait AkkOpError {
    val statusCode: Int
    val data: String
  }

  case class NotFound(data: String, statusCode: Int = StatusCodes.NotFound.intValue) extends AkkOpError
  case class BadRequest(data: String, statusCode: Int = StatusCodes.BadRequest.intValue) extends AkkOpError

}
