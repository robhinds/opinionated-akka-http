package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.StatusCodes
import io.github.robhinds.akkops.model.core.Errors._
import io.github.robhinds.akkops.model.core.Response.Response
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future

class ErrorHandlerSpec extends FlatSpec with Matchers
  with DefaultErrorHandler {

  case class TestData(num: Int, word: String)
  def wrap(in: TestData): Future[Response[TestData]] = Future.successful(Right(in))
  def wrap(in: ErrorResponse): Future[Response[TestData]] = Future.successful(Left(in))

  "Status code" should "be 404 for not found errors" in {
    errorStatusCode(NotFound()) shouldBe StatusCodes.NotFound
  }
  "Status code" should "be 400 for bad requesterrors" in {
    errorStatusCode(BadRequest()) shouldBe StatusCodes.BadRequest
  }
  "Status code" should "be 401 for not authorised errors" in {
    errorStatusCode(Unauthorized()) shouldBe StatusCodes.Unauthorized
  }
  "Status code" should "be 500 for anything else" in {
    errorStatusCode(InternalServerError()) shouldBe StatusCodes.InternalServerError
  }

}
