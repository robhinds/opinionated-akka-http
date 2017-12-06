package io.github.robhinds.getloose.model.core

import cats.syntax.either._
import io.github.robhinds.getloose.model.core.Errors.AkkOpError

object Response {

  type Response[T] = Either[AkkOpError, T]

}
