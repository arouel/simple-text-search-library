package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;

@Immutable(singleton = true)
abstract class Empty extends Matcher {

  @Override
  Matcher and(Matcher other) {
    return other;
  }

  @Override
  Matcher append(Matcher other) {
    return other;
  }

  @Override
  public
  boolean matches(String text) {
    return false;
  }

  @Override
  Matcher not() {
    return this;
  }

  @Override
  Matcher or(Matcher other) {
    return other;
  }

  @Override
  public String toString() {
    return "<>";
  }
}
