package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
abstract class Word extends Matcher {

  @Override
  Matcher append(Matcher expression) {
    return this;
  }

  @Override
  public boolean matches(String text) {
    return text.toLowerCase().contains(value().toLowerCase());
  }

  @Override
  Matcher normalize() {
    if (value().trim().isEmpty()) {
      return empty();
    }
    return this;
  }

  @Override
  public String toString() {
    return value();
  }

  @Parameter
  abstract String value();
}
