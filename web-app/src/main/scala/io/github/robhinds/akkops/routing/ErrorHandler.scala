package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.StatusCode
import io.github.robhinds.akkops.model.core.Errors.ErrorResponse

trait ErrorHandler {
  def errorStatusCode(er: ErrorResponse): StatusCode
}
