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
    return ImmutableNot.of(word(value));
  }

  static Matcher or(Matcher left, Matcher right) {
    return ImmutableOr.of(left, right);
  }

  static Matcher phrase(String value) {
    return ImmutablePhrase.of(value);
  }

  static Matcher word(String value) {
    return ImmutableWord.of(value);
  }

  Matcher and(Matcher other) {
    return ImmutableAnd.of(this, other);
  }

  final Matcher and(String value) {
    return and(word(value));
  }

  abstract Matcher append(Matcher other);

  final boolean isEmpty() {
    return this instanceof Empty;
  }

  public abstract boolean matches(String text);

  Matcher normalize() {
    return this;
  }

  Matcher or(Matcher other) {
    return ImmutableOr.of(this, other);
  }

  final Matcher or(String value) {
    return or(word(value));
  }

  @Override
  public abstract String toString();
}
