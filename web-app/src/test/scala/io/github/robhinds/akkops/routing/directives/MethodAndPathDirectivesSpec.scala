package io.github.robhinds.akkops.routing.directives

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.server.Directives._


class MethodAndPathDirectivesSpec extends FlatSpec with Matchers with ScalatestRouteTest with MethodAndPathDirectives {

  val smallRoute =
    getPath("test") { complete { "GET" } } ~
    putPath("test") { complete { "PUT" } } ~
    postPath("test") { complete { "POST" } } ~
    deletePath("test") { complete { "DELETE" } } ~
    headPath("test") { complete { "HEAD" } }


  "using getPath directive" should "behave as normal get" in {
    Get("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "GET"
    }
  }

  "using putPath directive" should "behave as normal put" in {
    Put("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "PUT"
    }
  }

  "using postPath directive" should "behave as normal post" in {
    Post("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "POST"
    }
  }

  "using deletePath directive" should "behave as normal delete" in {
    Delete("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "DELETE"
    }
  }

  "using headPath directive" should "behave as normal head" in {
    Head("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "HEAD"
    }
  }

}