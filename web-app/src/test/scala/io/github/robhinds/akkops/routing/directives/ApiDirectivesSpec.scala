package io.github.robhinds.akkops.routing.directives

import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server.Directives._


class ApiDirectivesSpec extends FlatSpec with Matchers with ScalatestRouteTest with ApiDirectives {

  val smallRoute = api (
    get {
      path("test") {
        complete {
          "not gonna happens"
        }
      }
    }
  )


  "accessing a trailing slash url" should "be redirected to no trailing slash" in {
    Get("/test/") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "This and all future requests should be directed to <a href=\"http://example.com/test\">this URI</a>."
    }
  }

}