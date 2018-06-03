package io.github.robhinds.akkops.routing

import akka.http.scaladsl.model.StatusCodes
import org.scalatest.{AsyncFlatSpec, Matchers}
import io.circe.generic.auto._
import io.circe._
import io.circe.parser._
import io.github.robhinds.akkops.model.core.Errors.{ErrorResponse, BadRequest}
import io.github.robhinds.akkops.model.core.Response.Response

import scala.concurrent.Future

class ResponseHandlerSpec extends AsyncFlatSpec with Matchers
  with ResponseHandler
  with DefaultResponseWrapperEncoder
  with DefaultErrorHandler {

  case class TestData(num: Int, word: String)
  def wrap(in: TestData): Future[Response[TestData]] = Future.successful(Right(in))
  def wrap(in: ErrorResponse): Future[Response[TestData]] = Future.successful(Left(in))

  "Status code" should "default to 200 OK" in {
    responseTuple(wrap(TestData(1,"hello"))) map {
      case (statusCode, _) =>
        statusCode should be (StatusCodes.OK)
    }
  }

  "Status code" should "be configurable" in {
    responseTuple(wrap(TestData(1,"hello")), StatusCodes.Created) map {
      case (statusCode, _) =>
        statusCode should be (StatusCodes.Created)
    }
  }

  "Status code" should "be consistent across top level and json" in {
    responseTuple(wrap(TestData(1,"hello")), StatusCodes.Created) map {
      case (statusCode, j) =>
        statusCode should be (StatusCodes.Created)
        parse(j) match {
          case Right(json) =>
            json.asObject.get.toMap("status").toString should be ("\"201 Created\"")
        }
    }
  }

  "response" should "be serialised to json correctly" in {
    responseTuple(wrap(TestData(1,"hello"))) map {
      case (_, j) =>
        j shouldBe "{\n  \"status\" : \"200 OK\",\n  \"data\" : {\n    \"num\" : 1,\n    \"word\" : \"hello\"\n  }\n}"
    }
  }

  "response" should "serialise errors to json correctly" in {
    responseTuple(wrap(BadRequest("Badly formatted request"))) map {
      case (_, j) =>
        j shouldBe "{\n  \"status\" : \"400 Bad Request\",\n  \"data\" : {\n    \"BadRequest\" : {\n      \"data\" : \"Badly formatted request\"\n    }\n  }\n}"
    }
  }

}
