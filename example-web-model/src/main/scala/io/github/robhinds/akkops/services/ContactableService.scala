package io.github.robhinds.akkops.services

import io.github.robhinds.akkops.model.core.Actions.GetUserRequest
import io.github.robhinds.akkops.model.core.Response.Response
import io.github.robhinds.akkops.model.domain.{Channel, Contactable, User}

import scala.concurrent.Future

trait ContactableService {

  def getAllUsers: Future[Response[Seq[User]]]
  def getAllChannels: Future[Response[Seq[Channel]]]
  def getContactable(request: GetUserRequest): Future[Response[Contactable]]

}

trait ContactableServiceComponent {
  def contactableService: ContactableService
}
