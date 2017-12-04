package io.github.robhinds.getloose.services

import io.github.robhinds.getloose.model.Actions.GetUserRequest
import io.github.robhinds.getloose.model.{Channel, Contactable, User}

trait ContactableService {

  def getAllUsers: Seq[User]

  def getAllChannels: Seq[Channel]

  def getContactable(request: GetUserRequest): Option[Contactable]

}

trait ContactableServiceComponent {
  def contactableService: ContactableService
}
