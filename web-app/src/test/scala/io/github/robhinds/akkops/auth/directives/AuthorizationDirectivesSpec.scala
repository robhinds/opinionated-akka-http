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
        roleOf("ADMIN") { _ =>
          complete {
            "logged in admin user"
          }
        }
      } ~
      path("login") {
        login(UserSession(UUID.randomUUID, "user", List("ADMIN"))) {
          complete("Ok")
        }
      } ~
      path("logout") {
        logout {
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

  "logging in as admin" should "allow user to access endpoints" in {
    import akka.http.scaladsl.model.headers._

    Get("/login") ~> smallRoute ~> check {
      val c = response.headers.collect {
        case c: `Set-Cookie` => c.cookie
      }

      println(c)
      val sessionData = c.head
      val refreshToken = c(1)


      Get("/test") ~> addHeaders(Cookie(sessionData.name, sessionData.value), Cookie(refreshToken.name, refreshToken.value)) ~> smallRoute ~> check {
        responseAs[String] shouldEqual "logged in admin user"
      }
    }
  }

  "logging out" should "clear cookies" in {
    import akka.http.scaladsl.model.headers._

    Get("/login") ~> smallRoute ~> check {
      val c = response.headers.collect {
        case c: `Set-Cookie` => c.cookie
      }

      val sessionData = c.head
      val refreshToken = c(1)

      Get("/logout") ~> addHeaders(Cookie(sessionData.name, sessionData.value), Cookie(refreshToken.name, refreshToken.value)) ~> smallRoute ~> check {
        val c = response.headers.collect {
          case c: `Set-Cookie` => c.cookie
        }

        val sessionDataLoggedOut = c.head
        val refreshTokenLoggedOut = c(1)

        sessionDataLoggedOut.value shouldBe "deleted"
        refreshTokenLoggedOut.value shouldBe "deleted"
      }
    }
  }

}