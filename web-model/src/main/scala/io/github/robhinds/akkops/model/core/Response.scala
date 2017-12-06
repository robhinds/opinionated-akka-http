package io.github.robhinds.akkops.model.core

import cats.syntax.either._
import io.github.robhinds.akkops.model.core.Errors.AkkOpError

object Response {

  type Response[T] = Either[AkkOpError, T]

}
