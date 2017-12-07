package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import io.circe.{Encoder, Json}
import io.github.robhinds.akkops.model.core.Errors.AkkOpError
import io.github.robhinds.akkops.model.core.Response.Response

trait ResponseHandler {
  this: ResponseWrapperEncoder =>
  import io.circe.syntax._
  import io.circe.generic.auto._

  def respond[A](response: Response[A], successStatusCode: StatusCode = StatusCodes.OK)
    (implicit ee: Encoder[AkkOpError], te: Encoder[A]) = {
    response match {
      case Left(er) => complete(
        StatusCode.int2StatusCode(er.statusCode),
        wrap(StatusCode.int2StatusCode(er.statusCode), er.asJson).toString
      )
      case Right(a) => complete(
        successStatusCode,
        wrap(successStatusCode, a.asJson).toString
      )
    }
  }
}

trait ResponseWrapperEncoder {
  def wrap(status: StatusCode, data: Json, metaData: Option[Json] = None): Json
}
