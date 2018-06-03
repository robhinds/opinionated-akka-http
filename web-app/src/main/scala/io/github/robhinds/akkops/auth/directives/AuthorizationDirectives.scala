package io.github.robhinds.akkops.auth.directives

import akka.http.scaladsl.server.{Directive0, Directive1}
import io.github.robhinds.akkops.auth.UserSession
import com.typesafe.scalalogging.LazyLogging

trait AuthorizationDirectives extends LazyLogging {

  def roleOf(r: String): Directive1[UserSession]

  def login(u: String, p: String): Directive0

  def logout: Directive0
}
