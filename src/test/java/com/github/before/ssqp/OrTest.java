package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.or;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OrTest {

  @Test
  public void testAppend_leftEmpty() {
    assertThat(or(empty(), empty()).append(term("a"))).isEqualTo(or(term("a"), empty()));
  }

  @Test
  public void testAppend_nothingEmpty() {
    assertThat(or(term("a"), term("b")).append(term("c"))).isEqualTo(or(term("a"), term("b").or("c")));
  }

  @Test
  public void testAppend_rightAnd_innerAppend() {
    assertThat(or(term("a"), and(empty(), empty())).append(term("b"))).isEqualTo(or(term("a"), term("b").and(empty())));
    assertThat(or(term("a"), and(term("b"), empty())).append(term("c"))).isEqualTo(or(term("a"), term("b").or("c")));
  }

  @Test
  public void testAppend_rightEmpty() {
    assertThat(or(term("a"), empty()).append(term("b"))).isEqualTo(or(term("a"), term("b")));
  }

  @Test
  public void testAppend_rightEmpty_appendAnd() {
    assertThat(or(term("a"), empty()).append(and(empty(), empty()))).isEqualTo(or(term("a"), and(empty(), empty())));
  }

  @Test
  public void testMatches() {
    assertThat(term("a").or(term("b")).matches("")).isFalse();
    assertThat(term("a").or(term("b")).matches("a")).isTrue();
    assertThat(term("a").or(term("b")).matches("a b")).isTrue();
    assertThat(term("a").or(term("b")).matches("a b c")).isTrue();
    assertThat(term("a").or(term("b")).matches("a b c d")).isTrue();
    assertThat(term("a").or(term("b")).matches("a c")).isTrue();
    assertThat(term("a").or(term("b")).matches("a c b")).isTrue();
    assertThat(term("a").or(term("b")).matches("a c b d")).isTrue();
    assertThat(term("a").or(term("b")).matches("a c d")).isTrue();
    assertThat(term("a").or(term("b")).matches("c d")).isFalse();
    assertThat(term("a").or(term("b")).matches("c d e")).isFalse();
  }

  @Test
  public void testNormalize_full() {
    assertThat(or(term("a"), term("b")).normalize()).isEqualTo(term("a").or(term("b")));
  }

  @Test
  public void testNormalize_leftEmpty() {
    assertThat(or(empty(), term("a")).normalize()).isEqualTo(term("a"));
  }

  @Test
  public void testNormalize_nested() {
    assertThat(or(term("a"), or(term("b"), empty())).normalize()).isEqualTo(term("a").or(term("b")));
    assertThat(or(term("a"), or(empty(), or(term("b"), empty()))).normalize()).isEqualTo(term("a").or(term("b")));
  }

  @Test
  public void testNormalize_onlyEmpty() {
    assertThat(or(empty(), empty()).normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_rightEmpty() {
    assertThat(or(term("a"), empty()).normalize()).isEqualTo(term("a"));
  }

}
