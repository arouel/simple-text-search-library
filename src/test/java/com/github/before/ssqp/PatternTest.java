package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.phrase;
import static com.github.before.ssqp.Matcher.word;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PatternTest {

  private static final String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

  @Test
  public void testCompile_and_termA() {
    assertThat(Pattern.compile(" a")).isEqualTo(word("a"));
  }

  @Test
  public void testCompile_closingBracketOnly() {
    assertThat(Pattern.compile(")")).isEqualTo(empty());
  }

  @Test
  public void testCompile_longTest() {
    Matcher matcher = Pattern.compile("asiofhiuahfaousfiuhufhiasuhfsha uaihsduihsuidhahsdhaishd ausidhuasidhashdiahs");
    assertThat(matcher).isEqualTo(
        word("asiofhiuahfaousfiuhufhiasuhfsha").and(word("uaihsduihsuidhahsdhaishd").and("ausidhuasidhashdiahs")));
  }

  @Test
  public void testCompile_minusAndOpeningBracket() {
    assertThat(Pattern.compile("-(")).isEqualTo(empty());
  }

  @Test
  public void testCompile_minusOnly() {
    assertThat(Pattern.compile("-")).isEqualTo(empty());
  }

  @Test
  public void testCompile_notTermA() {
    assertThat(Pattern.compile("-a")).isEqualTo(not("a"));
  }

  @Test
  public void testCompile_openingAndClosingBracketOnly() {
    assertThat(Pattern.compile("()")).isEqualTo(empty());
  }

  @Test
  public void testCompile_openingBracketOnly() {
    assertThat(Pattern.compile("(")).isEqualTo(empty());
  }

  @Test
  public void testCompile_or_termA() {
    assertThat(Pattern.compile(",a")).isEqualTo(word("a"));
  }

  @Test
  public void testCompile_phraseWithSpecialCharacters() {
    assertThat(Pattern.compile("\",.?!:;-/\\[]{}@#*&\"")).isEqualTo(phrase(",.?!:;-/\\[]{}@#*&"));
  }

  @Test
  public void testCompile_quotationMark_manyTerms() {
    assertThat(Pattern.compile("\"ab cd\"")).isEqualTo(phrase("ab cd"));
  }

  @Test
  public void testCompile_quotationMark_oneTerm() {
    assertThat(Pattern.compile("\"abcd\"")).isEqualTo(phrase("abcd"));
  }

  @Test
  public void testCompile_quotationMark_scientificalValue() {
    assertThat(Pattern.compile("\"2.4mm\"")).isEqualTo(phrase("2.4mm"));
    assertThat(Pattern.compile("\"2,4mm\"")).isEqualTo(phrase("2,4mm"));
  }

  @Test
  public void testCompile_quotationMarkOnly() {
    assertThat(Pattern.compile("\"")).isEqualTo(empty());
  }

  @Test
  public void testCompile_sentence() {
    assertThat(Pattern.compile("\"How are you? Fine.\"")).isEqualTo(phrase("How are you? Fine."));
  }

  @Test
  public void testCompile_spaceOnly() {
    assertThat(Pattern.compile(" ")).isEqualTo(empty());
    assertThat(Pattern.compile("    ")).isEqualTo(empty());
  }

  @Test
  public void testCompile_termA() {
    assertThat(Pattern.compile("a")).isEqualTo(word("a"));
  }

  @Test
  public void testCompile_termA_and_inBrackets_termB() {
    assertThat(Pattern.compile("a (b)")).isEqualTo(word("a").and("b"));
  }

  @Test
  public void testCompile_termA_and_inBrackets_termB_and_termC() {
    assertThat(Pattern.compile("a (b c)")).isEqualTo(word("a").and(word("b").and("c")));
  }

  @Test
  public void testCompile_termA_and_termB() {
    assertThat(Pattern.compile("a b")).isEqualTo(word("a").and("b"));
  }

  @Test
  public void testCompile_termA_or_inBrackets_termB_and_notTermC() {
    assertThat(Pattern.compile("a,(b -c)")).isEqualTo(word("a").or(word("b").andNot("c")));
  }

  @Test
  public void testCompile_termA_or_termB() {
    assertThat(Pattern.compile("a,b")).isEqualTo(word("a").or(word("b")));
    assertThat(Pattern.compile("a, b")).isEqualTo(word("a").or(word("b")));
    assertThat(Pattern.compile("a , b")).isEqualTo(word("a").or(word("b")));
  }

  @Test
  public void testMatches_examples() {
    assertThat(Pattern.compile("lorem").matches(text)).isTrue();
    assertThat(Pattern.compile("lorem -sed").matches(text)).isFalse();
    assertThat(Pattern.compile("(lorem -sad), (ipsum -amot)").matches(text)).isTrue();
    assertThat(Pattern.compile("(lorem -sed), (ipsum -amet)").matches(text)).isFalse();
    assertThat(Pattern.compile("(lorem -sed), (ipsum -amet), (sed eirmod dolor)").matches(text)).isTrue();
    assertThat(Pattern.compile("\"ipsum dolor\"").matches(text)).isTrue();
    assertThat(Pattern.compile("\"ipsum dolor\" \"magna aliquyam\"").matches(text)).isTrue();
    assertThat(Pattern.compile("\"ipsum dolor\" -\"magna aliquyam\"").matches(text)).isFalse();
  }

  @Test
  public void testMatches_notTerm() {
    assertThat(Pattern.compile("ipsum -elitr").matches(text)).isFalse();
  }

  @Test
  public void testMatches_singleTerm() {
    assertThat(Pattern.compile("ipsum").matches(text)).isTrue();
  }
}
