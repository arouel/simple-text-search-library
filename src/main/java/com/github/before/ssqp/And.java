package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
abstract class And extends Matcher {

  @Override
  Matcher append(Matcher other) {
    if (left().isEmpty()) {
      return withLeft(other);
    }
    if (right().isEmpty()) {
      return withRight(other);
    }
    if (right() instanceof And) {
      return withRight(right().append(other));
    }
    return withRight(and(right(), other));
  }

  @Parameter(order = 0)
  abstract Matcher left();

  @Override
  public
  boolean matches(String text) {
    return left().matches(text) && right().matches(text);
  }

  @Parameter(order = 2)
  abstract Matcher right();

  @Override
  Matcher simplify() {
    if (left().isEmpty()) {
      return right().simplify();
    }
    if (right().isEmpty()) {
      return left().simplify();
    }
    return and(left().simplify(), right().simplify());
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    if (left().isEmpty()) {
    } else if (left() instanceof Or) {
      b.append('(');
      b.append(left());
      b.append(')');
    } else {
      if (right() instanceof Empty) {
        b.append('(');
      }
      b.append(left());
      if (right() instanceof Empty) {
        b.append(')');
      }
    }
    if (right().isEmpty()) {
    } else if (right() instanceof Or) {
      if (!left().isEmpty()) {
        b.append(' ');
      }
      b.append('(');
      b.append(right());
      b.append(')');
    } else {
      if (!left().isEmpty()) {
        b.append(' ');
      }
      if (left() instanceof Empty) {
        b.append('(');
      }
      b.append(right());
      if (left() instanceof Empty) {
        b.append(')');
      }
    }
    return b.toString();
  }

  abstract And withLeft(Matcher expression);

  abstract And withRight(Matcher expression);
}
