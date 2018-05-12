package io.github.robhinds.akkops.auth.directives

import java.util.UUID

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.robhinds.akkops.auth.UserSession
import org.scalatest.{FlatSpec, Matchers}


class AuthorizationDirectivesSpec extends FlatSpec with Matchers with ScalatestRouteTest with AuthorizationDirectives {

  val smallRoute = Route.seal(
    get {
      path("test") {
        roleOf("ADMIN") { user =>
          complete {
            "not gonna happens"
          }
        }
      } ~
      path("login") {
        login(UserSession(UUID.randomUUID, "user", List("ADMIN"))) {
          complete("Ok")
        }
      }
    }
  )

  "accessing a specific endpoint that requires a roles" should "be rejected" in {
    Get("/test") ~> smallRoute ~> check {
      responseAs[String] shouldEqual "The supplied authentication is not authorized to access this resource"
    }
  }

//  "logging in as admin" should "allow user to access endpoints" in {
//    import akka.http.scaladsl.model.headers._
//    Get("/login") ~> smallRoute ~> check {
//      val cookies = response.headers.collect {
//        case c: `Set-Cookie` => c.cookie
//      }
//      println(cookies)
//      responseAs[String] shouldEqual "The supplied authentication is not authorized to access this resource"
//    }
//  }

}