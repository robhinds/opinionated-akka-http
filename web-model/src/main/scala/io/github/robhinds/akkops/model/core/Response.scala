package io.github.robhinds.akkops.model.core

import io.github.robhinds.akkops.model.core.Errors.ErrorResponse

object Response {

  type Response[T] = Either[ErrorResponse, T]

}
