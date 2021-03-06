package io.github.robhinds.akkops.model.core

import java.util.UUID

object Actions {

  sealed trait Action
  case class GetUserRequest(handle: Option[String], uuid: Option[UUID]) extends Action
}
