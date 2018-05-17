package io.github.robhinds.akkops.model.core

import io.github.robhinds.akkops.model.core.Errors.{AuthenticationError, ErrorResponse, NotFound}

object Response {

  type AuthenticationResponse[T] = Either[AuthenticationError, T]
  type Response[T] = Either[ErrorResponse, T]

  def success[T](t: T): Response[T] = Right(t)
  def failure[T](e: ErrorResponse): Response[T] = Left(e)
  def fromOption[T](o: Option[T], e: ErrorResponse = NotFound("Lookup not found")): Response[T] = o match {
    case Some(x) => success(x)
    case _ => failure[T](e)
  }

}
