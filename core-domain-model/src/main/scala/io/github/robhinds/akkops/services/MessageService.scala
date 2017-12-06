package io.github.robhinds.akkops.services

import io.github.robhinds.akkops.model.core.Actions.GetUserRequest
import io.github.robhinds.akkops.model.core.Response.Response
import io.github.robhinds.akkops.model.domain.Message

trait MessageService {

  def getMessages(recipient: GetUserRequest): Response[Seq[Message]]
  def sendMessage(content: String, recipient: GetUserRequest): Response[Message]

}

trait MessageServiceComponent {
  def messageService: MessageService
}
