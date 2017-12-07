package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.StatusCode
import io.circe.Json

object DefaultResponseHandler extends ResponseHandler with DefaultResponseWrapperEncoder

trait DefaultResponseWrapperEncoder extends ResponseWrapperEncoder {
  def wrap(status: StatusCode, data: Json, metaData: Option[Json]): Json = {
    Json.fromFields(List(
      ("status", Json.fromString(status.value)),
      ("data", data),
    ))
  }
}