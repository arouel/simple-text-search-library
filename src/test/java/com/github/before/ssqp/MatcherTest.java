package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.or;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MatcherTest {

  @Test
  public void testAppend_emptyOnEmpty() {
    assertThat(empty().append(empty())).isEqualTo(empty());
  }

  @Test
  public void testAppend_emptyOnTerm() {
    assertThat(word("a").append(empty())).isEqualTo(word("a"));
  }

  @Test
  public void testAppend_termOnTerm() {
    assertThat(word("a").append(word("b"))).isEqualTo(word("a"));
  }

  @Test
  public void testToString_andMix() {
    // left
    assertThat(and(word("a").or("b"), word("c")).toString()).isEqualTo("(a, b) c");
    assertThat(and(not(word("a").or("b")), word("c")).toString()).isEqualTo("-(a, b) c");
    // right
    assertThat(and(word("a"), word("b").or("c")).toString()).isEqualTo("a (b, c)");
    assertThat(and(word("a"), not(word("b").or("c"))).toString()).isEqualTo("a -(b, c)");
  }

  @Test
  public void testToString_complex() {
    Matcher expression1 = or(word("a"), not(or(word("b"), and(and(word("c"), not(word("d"))), word("e")))));
    assertThat(expression1.toString()).isEqualTo("a, -(b, (c -d e))");
  }

  @Test
  public void testToString_orMix() {
    // left
    assertThat(or(word("a").and("b"), word("c")).toString()).isEqualTo("(a b), c");
    assertThat(or(not(word("a").and("b")), word("c")).toString()).isEqualTo("-(a b), c");
    // right
    assertThat(or(word("a"), word("b").and("c")).toString()).isEqualTo("a, (b c)");
    assertThat(or(word("a"), not(word("b").and("c"))).toString()).isEqualTo("a, -(b c)");
  }

  @Test
  public void testToString_orOnly() {
    // left
    assertThat(or(word("a").or("b"), word("c")).toString()).isEqualTo("a, b, c");
    assertThat(or(not(word("a").or("b")), word("c")).toString()).isEqualTo("-(a, b), c");
    // right
    assertThat(or(word("a"), word("b").or("c")).toString()).isEqualTo("a, b, c");
    assertThat(or(word("a"), not(word("b").or("c"))).toString()).isEqualTo("a, -(b, c)");
  }

  @Test
  public void testToString_primitive() {
    assertThat(word("a").toString()).isEqualTo("a");
    assertThat(word("a").and("b").toString()).isEqualTo("a b");
    assertThat(word("a").and(not("b")).toString()).isEqualTo("a -b");
  }
}
