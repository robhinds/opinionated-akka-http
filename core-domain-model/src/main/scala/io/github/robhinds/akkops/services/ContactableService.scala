package io.github.robhinds.akkops.services

import io.github.robhinds.akkops.model.core.Actions.GetUserRequest
import io.github.robhinds.akkops.model.core.Response.Response
import io.github.robhinds.akkops.model.domain.{Channel, Contactable, User}

trait ContactableService {

  def getAllUsers: Response[Seq[User]]
  def getAllChannels: Response[Seq[Channel]]
  def getContactable(request: GetUserRequest): Response[Contactable]

}

trait ContactableServiceComponent {
  def contactableService: ContactableService
}
