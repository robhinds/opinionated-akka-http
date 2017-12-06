package io.github.robhinds.akkops.routing.directives

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive, PathMatcher}

trait MethodAndPathDirectives {
  def getPath[L](x: PathMatcher[L]): Directive[L] = get & path(x)
  def postPath[L](x: PathMatcher[L]): Directive[L] = post & path(x)
  def putPath[L](x: PathMatcher[L]): Directive[L] = put & path(x)
  def deletePath[L](x: PathMatcher[L]): Directive[L] = delete & path(x)
  def headPath[L](x: PathMatcher[L]): Directive[L] = head & path(x)
}
