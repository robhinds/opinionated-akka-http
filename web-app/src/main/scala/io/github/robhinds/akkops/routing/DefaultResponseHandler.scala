package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.StatusCode
import io.circe.Json

object DefaultResponseHandler extends ResponseHandler with DefaultResponseWrapperEncoder

trait DefaultResponseWrapperEncoder extends ResponseWrapperEncoder {
  def wrap(status: StatusCode, data: Json, metaData: Option[Json]): Json = {
    val json = Json.fromFields(List(
      ("status", Json.fromString(status.value)),
      ("data", data)
    ))
    metaData match {
      case Some(j) => j.deepMerge(json)
      case _ => json
    }
  }
}