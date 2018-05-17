package io.github.robhinds.akkops.model.core

object Errors {

  sealed trait ErrorResponse {
    val data: String
  }
  case class NotFound(data: String = "Not found") extends ErrorResponse
  case class BadRequest(data: String = "Bad request") extends ErrorResponse
  case class Unauthorized(data: String = "Unauthorized") extends ErrorResponse
  case class Forbidden(data: String = "Forbidden") extends ErrorResponse
  case class InternalServerError(data: String = "Internal server error") extends ErrorResponse

  sealed trait AuthenticationError extends ErrorResponse {
    val data: String
  }
  case class UserNotFound(data: String = "Username not found") extends AuthenticationError
  case class IncorrectCredentials(data: String = "Incorrect credentials provided") extends AuthenticationError
  case class AccountLocked(data: String = "Account locked") extends AuthenticationError
}
