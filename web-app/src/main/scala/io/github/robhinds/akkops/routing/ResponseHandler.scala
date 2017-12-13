package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.StandardRoute
import io.circe.{Encoder, Json}
import io.github.robhinds.akkops.model.core.Errors.AkkOpError
import io.github.robhinds.akkops.model.core.Response.Response

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ResponseHandler {
  this: ResponseWrapperEncoder =>
  import io.circe.syntax._
  import io.circe.generic.auto._

  def respond[A](response: Future[Response[A]], successStatusCode: StatusCode = StatusCodes.OK)
    (implicit ee: Encoder[AkkOpError], te: Encoder[A]): StandardRoute = {
    complete(
      response map {
        case Left(er) =>
          (StatusCode.int2StatusCode(er.statusCode),
          wrap(StatusCode.int2StatusCode(er.statusCode), er.asJson).toString)
        case Right(a) =>
          (successStatusCode,
          wrap(successStatusCode, a.asJson).toString)
      }
    )
  }
}

trait ResponseWrapperEncoder {
  def wrap(status: StatusCode, data: Json, metaData: Option[Json] = None): Json
}
