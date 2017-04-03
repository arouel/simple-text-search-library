package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class WordTest {

  @Test
  public void testMatches_contains() {
    assertThat(word("a").matches("b a c")).isTrue();
  }

  @Test
  public void testMatches_empty() {
    assertThat(word("a").matches("")).isFalse();
  }

  @Test
  public void testMatches_endsWith() {
    assertThat(word("a").matches("c b a")).isTrue();
  }

  @Test
  public void testMatches_evenWhenSurroundedByLetters() {
    assertThat(word("a").matches("aü")).isTrue();
    assertThat(word("a").matches("Ba")).isTrue();
    assertThat(word("a").matches("b")).isFalse();

    assertThat(word("a").matches("a,")).isTrue();
    assertThat(word("a").matches("a-")).isTrue();
    assertThat(word("a").matches("a?")).isTrue();
    assertThat(word("a").matches("?")).isFalse();

    assertThat(word("a").matches(",a")).isTrue();
    assertThat(word("a").matches(".a")).isTrue();
    assertThat(word("a").matches("!a")).isTrue();
    assertThat(word("a").matches(",")).isFalse();
  }

  @Test
  public void testMatches_isEqual() {
    assertThat(word("a").matches("a")).isTrue();
  }

  @Test
  public void testMatches_isNotEqual() {
    assertThat(word("a").matches("b")).isFalse();
  }

  @Test
  public void testMatches_startsWith() {
    assertThat(word("a").matches("a b c")).isTrue();
  }

  @Test
  public void testMatches_upperCaseLetter() {
    assertThat(word("a").matches("A")).isTrue();
  }

  @Test
  public void testNormalize_blankValue() {
    assertThat(word(" ").normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_emptyValue() {
    assertThat(word("").normalize()).isEqualTo(empty());
  }
}
