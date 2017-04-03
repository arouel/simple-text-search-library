package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.phrase;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NotTest {

  @Test
  public void testMatches_notTerm() {
    assertThat(not(word("a")).matches("")).isTrue();
    assertThat(not(word("a")).matches("a")).isFalse();
    assertThat(not(word("a")).matches("b")).isTrue();
    assertThat(not(word("a")).matches("b c")).isTrue();
    assertThat(not(word("a")).matches("b c d")).isTrue();
    assertThat(not(word("a")).matches("b c d a")).isFalse();
  }

  @Test
  public void testNormalize() {
    assertThat(not(empty()).normalize()).isEqualTo(empty());
    assertThat(not(word("a")).normalize()).isEqualTo(not(word("a")));
    assertThat(not(and(empty(), empty())).normalize()).isEqualTo(empty());
    assertThat(not(and(word("a"), empty())).normalize()).isEqualTo(not(word("a")));
  }

  @Test
  public void testToString_phrase() {
    assertThat(not(phrase("a b")).toString()).isEqualTo("-\"a b\"");
  }

  @Test
  public void testToString_word() {
    assertThat(not(word("a")).toString()).isEqualTo("-a");
  }
}
