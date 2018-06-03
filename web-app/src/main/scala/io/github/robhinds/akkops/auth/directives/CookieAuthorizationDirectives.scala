package io.github.robhinds.akkops.auth.directives

import akka.http.scaladsl.model.headers.HttpChallenges
import akka.http.scaladsl.server.AuthenticationFailedRejection.CredentialsRejected
import akka.http.scaladsl.server.Directives.{provide, reject}
import akka.http.scaladsl.server.{AuthenticationFailedRejection, Directive0, Directive1}
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._
import com.typesafe.scalalogging.LazyLogging
import io.github.robhinds.akkops.auth.{AuthenticationProvider, UserSession}

import scala.concurrent.ExecutionContext.Implicits.global

trait CookieAuthorizationDirectives extends AuthorizationDirectives with SessionDirectives with LazyLogging {
  authenticationProvider: AuthenticationProvider =>

  private val sessionConfig = SessionConfig.default("SECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRET")
  implicit private val sessionManager: SessionManager[UserSession] = new SessionManager[UserSession](sessionConfig)
  implicit private val refreshTokenStorage= new InMemoryRefreshTokenStorage[UserSession] {
    def log(msg: String) = logger.info(msg)
  }

  override def roleOf(r: String): Directive1[UserSession] = requiresRole(r, refreshable[UserSession], usingCookies)

  private def requiresRole(r: String, sc: SessionContinuity[UserSession], st: GetSessionTransport): Directive1[UserSession] =
    optionalSession(sc, st).flatMap {
      case Some(data) if data.roles.contains(r) => provide(data)
      case _ => reject(sc.clientSessionManager.sessionMissingRejection)
    }

  override def login(u: String, p: String): Directive0 =
    authenticationProvider.authenticate(u, p) match {
      case Right(s) => setSession(refreshable, usingCookies, s)
      case Left(_) => reject(AuthenticationFailedRejection(CredentialsRejected, HttpChallenges.basic("")))
    }

  override val logout: Directive0 = invalidateSession(refreshable, usingCookies)
}
