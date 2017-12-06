package io.github.robhinds.getloose.model.core

import akka.http.scaladsl.model.StatusCodes

object Errors {

  sealed trait AkkOpError {
    val statusCode: String
    val data: String
  }

  case class NotFound(data: String, statusCode: String = StatusCodes.NotFound.value) extends AkkOpError
  case class BadRequest(data: String, statusCode: String = StatusCodes.BadRequest.value) extends AkkOpError

}
