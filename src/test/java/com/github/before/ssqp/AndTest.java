package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AndTest {

  @Test
  public void testAppend_leftEmpty() {
    assertThat(and(empty(), empty()).append(term("a"))).isEqualTo(and(term("a"), empty()));
  }

  @Test
  public void testAppend_nestedAnd() {
    Matcher nestedAnd = and(term("a"), and(empty(), empty()));
    assertThat(nestedAnd.append(term("b"))).isEqualTo(and(term("a"), and(term("b"), empty())));
    assertThat(nestedAnd.append(term("b")).append(term("c"))).isEqualTo(and(term("a"), and(term("b"), term("c"))));
  }

  @Test
  public void testAppend_nothingEmpty() {
    assertThat(and(term("a"), term("b")).append(term("c"))).isEqualTo(and(term("a"), term("b").and("c")));
  }

  @Test
  public void testAppend_rightEmpty() {
    assertThat(and(term("a"), empty()).append(term("b"))).isEqualTo(and(term("a"), term("b")));
  }

  @Test
  public void testMatches() {
    assertThat(term("a").and(term("b")).matches("")).isFalse();
    assertThat(term("a").and(term("b")).matches("a")).isFalse();
    assertThat(term("a").and(term("b")).matches("a b")).isTrue();
    assertThat(term("a").and(term("b")).matches("a b c")).isTrue();
    assertThat(term("a").and(term("b")).matches("a b c d")).isTrue();
    assertThat(term("a").and(term("b")).matches("a c")).isFalse();
    assertThat(term("a").and(term("b")).matches("a c b")).isTrue();
    assertThat(term("a").and(term("b")).matches("a c b d")).isTrue();
    assertThat(term("a").and(term("b")).matches("a c d")).isFalse();
  }

  @Test
  public void testSimplify_full() {
    assertThat(and(term("a"), term("b")).simplify()).isEqualTo(term("a").and(term("b")));
  }

  @Test
  public void testSimplify_leftEmpty() {
    assertThat(and(empty(), term("a")).simplify()).isEqualTo(term("a"));
  }

  @Test
  public void testSimplify_nested() {
    assertThat(and(term("a"), and(term("b"), empty())).simplify()).isEqualTo(term("a").and(term("b")));
    assertThat(and(term("a"), and(empty(), and(term("b"), empty()))).simplify()).isEqualTo(term("a").and(term("b")));
  }

  @Test
  public void testSimplify_onlyEmpty() {
    assertThat(and(empty(), empty()).simplify()).isEqualTo(empty());
  }

  @Test
  public void testSimplify_rightEmpty() {
    assertThat(and(term("a"), empty()).simplify()).isEqualTo(term("a"));
  }

  @Test
  public void testToString_andLeft() {
    assertThat(and(term("a").and("b"), term("c")).toString()).isEqualTo("a b c");
    assertThat(and(not(term("a").and("b")), term("c")).toString()).isEqualTo("-(a b) c");
  }

  @Test
  public void testToString_andRight() {
    assertThat(and(term("a"), term("b").and("c")).toString()).isEqualTo("a b c");
    assertThat(and(term("a"), not(term("b").and("c"))).toString()).isEqualTo("a -(b c)");
  }

  @Test
  public void testToString_leftAndRightNonEmpty() {
    assertThat(and(term("a"), term("b")).toString()).isEqualTo("a b");
  }

  @Test
  public void testToString_leftEmpty() {
    assertThat(and(empty(), term("a")).toString()).isEqualTo("(a)");
  }

  @Test
  public void testToString_rightEmpty() {
    assertThat(and(term("a"), empty()).toString()).isEqualTo("(a)");
  }

}
