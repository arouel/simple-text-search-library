package com.github.before.ssqp;

public abstract class Matcher {

  static Matcher and(Matcher left, Matcher right) {
    return ImmutableAnd.of(left, right);
  }

  static Matcher empty() {
    return ImmutableEmpty.of();
  }

  static Matcher not(Matcher expression) {
    return ImmutableNot.of(expression);
  }

  static Matcher not(String value) {
    return ImmutableNot.of(term(value));
  }

  static Matcher or(Matcher left, Matcher right) {
    return ImmutableOr.of(left, right);
  }

  static Matcher term(String value) {
    return ImmutableTerm.of(value);
  }

  Matcher and(Matcher other) {
    return ImmutableAnd.of(this, other);
  }

  final Matcher and(String value) {
    return and(term(value));
  }

  final Matcher andNot(String value) {
    return and(not(term(value)));
  }

  abstract Matcher append(Matcher other);

  final boolean isEmpty() {
    return this instanceof Empty;
  }

  public abstract boolean matches(String text);

  Matcher not() {
    return ImmutableNot.of(this);
  }

  Matcher or(Matcher other) {
    return ImmutableOr.of(this, other);
  }

  final Matcher or(String value) {
    return or(term(value));
  }

  final Matcher orNot(String value) {
    return or(not(term(value)));
  }

  Matcher simplify() {
    return this;
  }

  @Override
  public abstract String toString();
}
