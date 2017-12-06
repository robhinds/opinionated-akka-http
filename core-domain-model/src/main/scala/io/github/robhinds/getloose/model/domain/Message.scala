package io.github.robhinds.getloose.model.domain

sealed trait Message {
  val content: String
  val recipients: Seq[Contactable]
}

case class PrivateMessage(content: String, recipients: Seq[User]) extends Message
case class PublicMessage(content: String, recipients: Seq[Channel]) extends Message