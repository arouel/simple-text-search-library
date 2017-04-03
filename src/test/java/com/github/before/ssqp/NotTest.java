package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NotTest {

  @Test
  public void testMatches_notTerm() {
    assertThat(not(term("a")).matches("")).isTrue();
    assertThat(not(term("a")).matches("a")).isFalse();
    assertThat(not(term("a")).matches("b")).isTrue();
    assertThat(not(term("a")).matches("b c")).isTrue();
    assertThat(not(term("a")).matches("b c d")).isTrue();
    assertThat(not(term("a")).matches("b c d a")).isFalse();
  }

  @Test
  public void testNormalize() {
    assertThat(not(empty()).normalize()).isEqualTo(empty());
    assertThat(not(term("a")).normalize()).isEqualTo(not(term("a")));
    assertThat(not(and(empty(), empty())).normalize()).isEqualTo(empty());
    assertThat(not(and(term("a"), empty())).normalize()).isEqualTo(not(term("a")));
  }
}
