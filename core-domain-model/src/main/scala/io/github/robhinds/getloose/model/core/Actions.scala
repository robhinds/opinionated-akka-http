package io.github.robhinds.getloose.model.core

object Actions {

  sealed trait Action
  case class GetUserRequest(handle: Option[String], uuid: Option[String]) extends Action
}
