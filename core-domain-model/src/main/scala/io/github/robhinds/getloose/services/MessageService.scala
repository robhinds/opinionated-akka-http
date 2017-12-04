package io.github.robhinds.getloose.services

import io.github.robhinds.getloose.model.Actions.GetUserRequest
import io.github.robhinds.getloose.model.Errors.ContactableError
import io.github.robhinds.getloose.model._

trait MessageService {

  def getMessages(recipient: GetUserRequest): Either[ContactableError, Seq[Message]]

  def sendMessage(content: String, recipient: GetUserRequest): Either[ContactableError, Message]

}
