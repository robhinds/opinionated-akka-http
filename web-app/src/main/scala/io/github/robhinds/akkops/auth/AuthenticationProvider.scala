package io.github.robhinds.akkops.auth

import io.github.robhinds.akkops.model.core.Response.AuthenticationResponse

trait AuthenticationProvider {

  def authenticate(u: String, p: String): AuthenticationResponse[UserSession]

}
