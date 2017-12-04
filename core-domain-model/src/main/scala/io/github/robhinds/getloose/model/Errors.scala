package io.github.robhinds.getloose.model

object Errors {

  sealed trait ContactableError
  case object ContactableNotFound extends ContactableError
  case object ContactableAlreadyExists extends ContactableError

}
