package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.or;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MatcherTest {

  @Test
  public void testAppend_emptyOnEmpty() {
    assertThat(empty().append(empty())).isEqualTo(empty());
  }

  @Test
  public void testAppend_emptyOnTerm() {
    assertThat(term("a").append(empty())).isEqualTo(term("a"));
  }

  @Test
  public void testAppend_termOnTerm() {
    assertThat(term("a").append(term("b"))).isEqualTo(term("a"));
  }

  @Test
  public void testToString_andMix() {
    // left
    assertThat(and(term("a").or("b"), term("c")).toString()).isEqualTo("(a, b) c");
    assertThat(and(not(term("a").or("b")), term("c")).toString()).isEqualTo("-(a, b) c");
    // right
    assertThat(and(term("a"), term("b").or("c")).toString()).isEqualTo("a (b, c)");
    assertThat(and(term("a"), not(term("b").or("c"))).toString()).isEqualTo("a -(b, c)");
  }

  @Test
  public void testToString_complex() {
    Matcher expression1 = or(term("a"), not(or(term("b"), and(and(term("c"), not(term("d"))), term("e")))));
    assertThat(expression1.toString()).isEqualTo("a, -(b, (c -d e))");
    Matcher expression2 = term("a").or(term("b").or(term("c").and(term("d").not()).and("e")).not());
    assertThat(expression2.toString()).isEqualTo("a, -(b, (c -d e))");
  }

  @Test
  public void testToString_orMix() {
    // left
    assertThat(or(term("a").and("b"), term("c")).toString()).isEqualTo("(a b), c");
    assertThat(or(not(term("a").and("b")), term("c")).toString()).isEqualTo("-(a b), c");
    // right
    assertThat(or(term("a"), term("b").and("c")).toString()).isEqualTo("a, (b c)");
    assertThat(or(term("a"), not(term("b").and("c"))).toString()).isEqualTo("a, -(b c)");
  }

  @Test
  public void testToString_orOnly() {
    // left
    assertThat(or(term("a").or("b"), term("c")).toString()).isEqualTo("a, b, c");
    assertThat(or(not(term("a").or("b")), term("c")).toString()).isEqualTo("-(a, b), c");
    // right
    assertThat(or(term("a"), term("b").or("c")).toString()).isEqualTo("a, b, c");
    assertThat(or(term("a"), not(term("b").or("c"))).toString()).isEqualTo("a, -(b, c)");
  }

  @Test
  public void testToString_primitive() {
    assertThat(term("a").toString()).isEqualTo("a");
    assertThat(term("a").and("b").toString()).isEqualTo("a b");
    assertThat(term("a").and(not("b")).toString()).isEqualTo("a -b");
  }
}
