package io.github.robhinds.akkops.model.core

object Actions {

  sealed trait Action
  case class GetUserRequest(handle: Option[String], uuid: Option[String]) extends Action
}
