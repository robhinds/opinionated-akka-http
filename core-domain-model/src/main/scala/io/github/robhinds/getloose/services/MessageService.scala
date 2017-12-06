package io.github.robhinds.getloose.services

import io.github.robhinds.getloose.model.core.Actions.GetUserRequest
import io.github.robhinds.getloose.model.core.Response.Response
import io.github.robhinds.getloose.model.domain.Message

trait MessageService {

  def getMessages(recipient: GetUserRequest): Response[Seq[Message]]
  def sendMessage(content: String, recipient: GetUserRequest): Response[Message]

}

trait MessageServiceComponent {
  def messageService: MessageService
}
