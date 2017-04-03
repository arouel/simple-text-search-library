package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EmptyTest {

  @Test
  public void testAppend() {
    assertThat(empty().append(empty())).isEqualTo(empty());
    assertThat(empty().append(word("a"))).isEqualTo(word("a"));
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
