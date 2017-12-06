package io.github.robhinds.getloose.services

import io.github.robhinds.getloose.model.core.Actions.GetUserRequest
import io.github.robhinds.getloose.model.core.Response.Response
import io.github.robhinds.getloose.model.domain.{Channel, Contactable, User}

trait ContactableService {

  def getAllUsers: Response[Seq[User]]
  def getAllChannels: Response[Seq[Channel]]
  def getContactable(request: GetUserRequest): Response[Contactable]

}

trait ContactableServiceComponent {
  def contactableService: ContactableService
}
