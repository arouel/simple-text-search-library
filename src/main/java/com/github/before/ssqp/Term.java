package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;

@Immutable
abstract class Term extends Matcher {

  @Override
  Matcher append(Matcher expression) {
    return this;
  }

  @Override
  public boolean matches(String text) {
    String filtered = CharMatcher.javaLetterOrDigit().or(CharMatcher.whitespace()).retainFrom(text);
    Iterable<String> splitted = Splitter.on(' ').omitEmptyStrings().split(filtered);
    return FluentIterable.from(splitted).firstMatch(new Predicate<String>() {
      @Override
      public boolean apply(String input) {
        return input.equalsIgnoreCase(value());
      }
    }).isPresent();
  }

  @Override
  Matcher simplify() {
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
