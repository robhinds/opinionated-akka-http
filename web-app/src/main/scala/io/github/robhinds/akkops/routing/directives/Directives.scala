package io.github.robhinds.akkops.routing.directives

trait Directives
  extends ApiDirectives
  with MethodAndPathDirectives

object Directives extends Directives