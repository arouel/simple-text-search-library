package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EmptyTest {

  @Test
  public void testAppend() {
    assertThat(empty().append(empty())).isEqualTo(empty());
    assertThat(empty().append(term("a"))).isEqualTo(term("a"));
  }

  @Test
  public void testMatches_notTerm() {
    assertThat(empty().matches("")).isFalse();
    assertThat(empty().matches(" ")).isFalse();
    assertThat(empty().matches("   ")).isFalse();
    assertThat(empty().matches("a")).isFalse();
    assertThat(empty().matches("a b")).isFalse();
  }
}
