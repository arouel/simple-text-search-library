package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.term;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TermTest {

  @Test
  public void testMatches_contains() {
    assertThat(term("a").matches("b a c")).isTrue();
  }

  @Test
  public void testMatches_empty() {
    assertThat(term("a").matches("")).isFalse();
  }

  @Test
  public void testMatches_endsWith() {
    assertThat(term("a").matches("c b a")).isTrue();
  }

  @Test
  public void testMatches_filterSpecialCharacters() {
    assertThat(term("a").matches("a,")).isTrue();
    assertThat(term("a").matches("a-")).isTrue();
    assertThat(term("a").matches("a?")).isTrue();
    assertThat(term("a").matches("aü")).isFalse();
  }

  @Test
  public void testMatches_isEqual() {
    assertThat(term("a").matches("a")).isTrue();
  }

  @Test
  public void testMatches_isNotEqual() {
    assertThat(term("a").matches("b")).isFalse();
  }

  @Test
  public void testMatches_startsWith() {
    assertThat(term("a").matches("a b c")).isTrue();
  }

  @Test
  public void testMatches_upperCaseLetter() {
    assertThat(term("a").matches("A")).isTrue();
  }

  @Test
  public void testNormalize_blankValue() {
    assertThat(term(" ").normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_emptyValue() {
    assertThat(term("").normalize()).isEqualTo(empty());
  }
}
