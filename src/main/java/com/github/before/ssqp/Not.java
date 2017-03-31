package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
abstract class Not extends Matcher {

  @Override
  Not append(Matcher other) {
    return withExpression(other);
  }

  @Parameter
  abstract Matcher expression();

  @Override
  public
  boolean matches(String text) {
    return !expression().matches(text);
  }

  @Override
  Matcher simplify() {
    Matcher expression = expression().simplify();
    if (expression.isEmpty()) {
      return empty();
    }
    return not(expression);
  }

  @Override
  public String toString() {
    return expression() instanceof Term ? "-" + expression() : "-(" + expression() + ")";
  }

  abstract Not withExpression(Matcher expression);
}
