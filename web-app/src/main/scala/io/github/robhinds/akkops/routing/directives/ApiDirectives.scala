package io.github.robhinds.akkops.routing.directives

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.redirectToNoTrailingSlashIfPresent
import akka.http.scaladsl.server.Route

trait ApiDirectives {
  def api(innerRoutes: => Route): Route =
    redirectToNoTrailingSlashIfPresent(StatusCodes.MovedPermanently) {
      innerRoutes
    }

}
