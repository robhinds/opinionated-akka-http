package io.github.robhinds.getloose.model

import java.util.UUID

import cats.data.ValidatedNel
import cats.data.Validated.{invalidNel, valid}

sealed trait Contactable {
  val uuid: UUID
  val handle: String
}

case class Channel (
  uuid: UUID,
  handle: String,
  members: Seq[User]
) extends Contactable
object Channel {
  def create(handle: String, members: Seq[User]) = {
    Channel(UUID.randomUUID(), handle, members)
  }
}

case class User(
  uuid: UUID,
  handle: String,
  email: EmailAddress
) extends Contactable

case class EmailAddress(value: String) extends AnyVal
object EmailAddress {
  final private[model] val validEmail = """^([a-zA-Z0-9.!#$%&’'*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)$""".r
  def validateEmail(email: String): ValidatedNel[Exception, EmailAddress] = email match {
    case validEmail(_, _) => valid(EmailAddress(email))
    case _ => invalidNel(new Exception(""))
  }
}

object User {
  def create(username: String, email: String) = {
    EmailAddress.validateEmail(email) map { ea =>
      User(UUID.randomUUID(), username, ea)
    }
  }
}
