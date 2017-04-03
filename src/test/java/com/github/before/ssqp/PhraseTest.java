package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.phrase;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PhraseTest {

  @Test
  public void testMatches_contains() {
    assertThat(phrase("a").matches("b a c")).isTrue();
  }

  @Test
  public void testMatches_empty() {
    assertThat(phrase("a").matches("")).isFalse();
  }

  @Test
  public void testMatches_endsWith() {
    assertThat(phrase("a").matches("c b a")).isTrue();
  }

  @Test
  public void testMatches_evenWhenSurroundedByLetters() {
    assertThat(phrase("a").matches("aü")).isTrue();
    assertThat(phrase("a").matches("Ba")).isTrue();
    assertThat(phrase("a").matches("b")).isFalse();

    assertThat(phrase("a").matches("a,")).isTrue();
    assertThat(phrase("a").matches("a-")).isTrue();
    assertThat(phrase("a").matches("a?")).isTrue();
    assertThat(phrase("a").matches("?")).isFalse();

    assertThat(phrase("a").matches(",a")).isTrue();
    assertThat(phrase("a").matches(".a")).isTrue();
    assertThat(phrase("a").matches("!a")).isTrue();
    assertThat(phrase("a").matches(",")).isFalse();
  }

  @Test
  public void testMatches_isEqual() {
    assertThat(phrase("a").matches("a")).isTrue();
  }

  @Test
  public void testMatches_isNotEqual() {
    assertThat(phrase("a").matches("b")).isFalse();
  }

  @Test
  public void testMatches_startsWith() {
    assertThat(phrase("a").matches("a b c")).isTrue();
  }

  @Test
  public void testMatches_upperCaseLetter() {
    assertThat(phrase("a").matches("A")).isTrue();
  }

  @Test
  public void testNormalize_blankValue() {
    assertThat(phrase(" ").normalize()).isEqualTo(empty());
  }

  @Test
  public void testNormalize_emptyValue() {
    assertThat(phrase("").normalize()).isEqualTo(empty());
  }

  @Test
  public void testToString() {
    assertThat(phrase("a b").toString()).isEqualTo("\"a b\"");
  }
}
