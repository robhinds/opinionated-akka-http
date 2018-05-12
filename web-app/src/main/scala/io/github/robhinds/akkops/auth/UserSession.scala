package io.github.robhinds.akkops.auth

import java.util.UUID
import com.softwaremill.session.{SessionSerializer, MultiValueSessionSerializer}
import scala.util.Try

case class UserSession(uuid: UUID, username: String, roles: List[String])

object UserSession {
  implicit def serializer: SessionSerializer[UserSession, String] =
    new MultiValueSessionSerializer(
      (s: UserSession) => Map("username" -> s.username, "uuid" -> s.uuid.toString, "roles" -> s.roles.mkString(",")),
      (m: Map[String, String]) => Try(
        UserSession(UUID.fromString(m("uuid")), m("username"), m("roles").split(",").toList)
      )
    )
}
