package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AndTest {

  @Test
  public void testAppend_leftEmpty() {
    assertThat(and(empty(), empty()).append(word("a"))).isEqualTo(and(word("a"), empty()));
  }

  @Test
  public void testAppend_nestedAnd() {
    Matcher nestedAnd = and(word("a"), and(empty(), empty()));
    assertThat(nestedAnd.append(word("b"))).isEqualTo(and(word("a"), and(word("b"), empty())));
    assertThat(nestedAnd.append(word("b")).append(word("c"))).isEqualTo(and(word("a"), and(word("b"), word("c"))));
  }

  @Test
  public void testAppend_nothingEmpty() {
    assertThat(and(word("a"), word("b")).append(word("c"))).isEqualTo(and(word("a"), word("b").and("c")));
  }

  @Test
  public void testAppend_rightEmpty() {
    assertThat(and(word("a"), empty()).append(word("b"))).isEqualTo(and(word("a"), word("b")));
  }

  @Test
  public void testMatches() {
    assertThat(word("a").and(word("b")).matches("")).isFalse();
    assertThat(word("a").and(word("b")).matches("a")).isFalse();
    assertThat(word("a").and(word("b")).matches("a b")).isTrue();
    assertThat(word("a").and(word("b")).matches("a b c")).isTrue();
    assertThat(word("a").and(word("b")).matches("a b c d")).isTrue();
    assertThat(word("a").and(word("b")).matches("a c")).isFalse();
    assertThat(word("a").and(word("b")).matches("a c b")).isTrue();
    assertThat(word("a").and(word("b")).matches("a c b d")).isTrue();
    assertThat(word("a").and(word("b")).matches("a c d")).isFalse();
  }

  @Test
  public void testNormalize_full() {
    assertThat(and(word("a"), word("b")).normalize()).isEqualTo(word("a").and(word("b")));
  }

  @Test
  public void testNormalize_leftEmpty() {
    assertThat(and(empty(), word("a")).normalize()).isEqualTo(word("a"));
  }

  @Test
  public void testNormalize_nested() {
    assertThat(and(word("a"), and(word("b"), empty())).normalize()).isEqualTo(word("a").and(word("b")));
    assertThat(and(word("a"), and(empty(), and(word("b"), empty()))).normalize()).isEqualTo(word("a").and(word("b")));
  }

  @Test
  public void testNormalize_onlyEmpty() {
    assertThat(and(empty(), empty()).normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_rightEmpty() {
    assertThat(and(word("a"), empty()).normalize()).isEqualTo(word("a"));
  }

  @Test
  public void testToString_andLeft() {
    assertThat(and(word("a").and("b"), word("c")).toString()).isEqualTo("a b c");
    assertThat(and(not(word("a").and("b")), word("c")).toString()).isEqualTo("-(a b) c");
  }

  @Test
  public void testToString_andRight() {
    assertThat(and(word("a"), word("b").and("c")).toString()).isEqualTo("a b c");
    assertThat(and(word("a"), not(word("b").and("c"))).toString()).isEqualTo("a -(b c)");
  }

  @Test
  public void testToString_leftAndRightNonEmpty() {
    assertThat(and(word("a"), word("b")).toString()).isEqualTo("a b");
  }

  @Test
  public void testToString_leftEmpty() {
    assertThat(and(empty(), word("a")).toString()).isEqualTo("(a)");
  }

  @Test
  public void testToString_rightEmpty() {
    assertThat(and(word("a"), empty()).toString()).isEqualTo("(a)");
  }

}
