package io.github.robhinds.akkops.auth.directives

import akka.http.scaladsl.server.{Directive0, Directive1}
import akka.http.scaladsl.server.Directives.{provide, reject}
import com.softwaremill.session._
import io.github.robhinds.akkops.auth.UserSession
import com.softwaremill.session.SessionOptions._
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext.Implicits.global

trait AuthorizationDirectives extends SessionDirectives with LazyLogging {

  val sessionConfig = SessionConfig.default("SERVER SECRET")
  implicit val sessionManager = new SessionManager[UserSession](sessionConfig)
  implicit val refreshTokenStorage = new InMemoryRefreshTokenStorage[UserSession] {
    def log(msg: String) = logger.info(msg)
  }

  def roleOf(r: String): Directive1[UserSession] = requiresRole(r, refreshable[UserSession], usingCookies)

  def requiresRole(r: String, sc: SessionContinuity[UserSession], st: GetSessionTransport): Directive1[UserSession] =
    optionalSession(sc, st).flatMap {
      case Some(data) if data.roles.contains(r) => provide(data)
      case _ => reject(sc.clientSessionManager.sessionMissingRejection)
    }


  def login(s: UserSession): Directive0 = setSession(refreshable, usingCookies, s)
  val logout = invalidateSession(refreshable, usingCookies)
}
