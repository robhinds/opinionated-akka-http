package io.github.robhinds.getloose.routing

import akka.http.scaladsl.server.Directives._
import io.circe.Encoder
import io.github.robhinds.getloose.model.core.Errors.AkkOpError
import io.github.robhinds.getloose.model.core.Response.Response

trait ResponseHandler {
  import io.circe.syntax._
  import io.circe.generic.auto._

  def respond[A](response: Response[A])(implicit ee: Encoder[AkkOpError], te: Encoder[A]) = {
    response match {
      case Left(er) => complete(er.asJson.toString)
      case Right(a) => complete(a.asJson.toString)
    }
  }
}

object ResponseHandler extends ResponseHandler
