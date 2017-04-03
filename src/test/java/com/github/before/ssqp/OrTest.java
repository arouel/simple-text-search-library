package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.or;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OrTest {

  @Test
  public void testAppend_leftEmpty() {
    assertThat(or(empty(), empty()).append(word("a"))).isEqualTo(or(word("a"), empty()));
  }

  @Test
  public void testAppend_nothingEmpty() {
    assertThat(or(word("a"), word("b")).append(word("c"))).isEqualTo(or(word("a"), word("b").or("c")));
  }

  @Test
  public void testAppend_rightAnd_innerAppend() {
    assertThat(or(word("a"), and(empty(), empty())).append(word("b"))).isEqualTo(or(word("a"), word("b").and(empty())));
    assertThat(or(word("a"), and(word("b"), empty())).append(word("c"))).isEqualTo(or(word("a"), word("b").or("c")));
  }

  @Test
  public void testAppend_rightEmpty() {
    assertThat(or(word("a"), empty()).append(word("b"))).isEqualTo(or(word("a"), word("b")));
  }

  @Test
  public void testAppend_rightEmpty_appendAnd() {
    assertThat(or(word("a"), empty()).append(and(empty(), empty()))).isEqualTo(or(word("a"), and(empty(), empty())));
  }

  @Test
  public void testMatches() {
    assertThat(word("a").or(word("b")).matches("")).isFalse();
    assertThat(word("a").or(word("b")).matches("a")).isTrue();
    assertThat(word("a").or(word("b")).matches("a b")).isTrue();
    assertThat(word("a").or(word("b")).matches("a b c")).isTrue();
    assertThat(word("a").or(word("b")).matches("a b c d")).isTrue();
    assertThat(word("a").or(word("b")).matches("a c")).isTrue();
    assertThat(word("a").or(word("b")).matches("a c b")).isTrue();
    assertThat(word("a").or(word("b")).matches("a c b d")).isTrue();
    assertThat(word("a").or(word("b")).matches("a c d")).isTrue();
    assertThat(word("a").or(word("b")).matches("c d")).isFalse();
    assertThat(word("a").or(word("b")).matches("c d e")).isFalse();
  }

  @Test
  public void testNormalize_full() {
    assertThat(or(word("a"), word("b")).normalize()).isEqualTo(word("a").or(word("b")));
  }

  @Test
  public void testNormalize_leftEmpty() {
    assertThat(or(empty(), word("a")).normalize()).isEqualTo(word("a"));
  }

  @Test
  public void testNormalize_nested() {
    assertThat(or(word("a"), or(word("b"), empty())).normalize()).isEqualTo(word("a").or(word("b")));
    assertThat(or(word("a"), or(empty(), or(word("b"), empty()))).normalize()).isEqualTo(word("a").or(word("b")));
  }

  @Test
  public void testNormalize_onlyEmpty() {
    assertThat(or(empty(), empty()).normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_rightEmpty() {
    assertThat(or(word("a"), empty()).normalize()).isEqualTo(word("a"));
  }

}
