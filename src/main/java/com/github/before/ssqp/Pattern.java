package com.github.before.ssqp;

import static com.github.before.ssqp.Matcher.and;
import static com.github.before.ssqp.Matcher.empty;
import static com.github.before.ssqp.Matcher.not;
import static com.github.before.ssqp.Matcher.or;
import static com.github.before.ssqp.Matcher.term;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Arrays;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.github.before.ssqp.ExprParser.AndContext;
import com.github.before.ssqp.ExprParser.CloseContext;
import com.github.before.ssqp.ExprParser.NotContext;
import com.github.before.ssqp.ExprParser.OpenContext;
import com.github.before.ssqp.ExprParser.OrContext;
import com.github.before.ssqp.ExprParser.TermContext;

public final class Pattern {

  public static Matcher compile(String query) {
    try {
      CodePointCharStream input = CharStreams.fromReader(new StringReader(query));
      ExprLexer lexer = new ExprLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      ExprParser parser = new ExprParser(tokens);
      ParseTree tree = parser.expr();
      Visitor visitor = new Visitor();
      visitor.visit(tree);
      return visitor.expression().simplify();
    } catch (IOException e) {
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  private static class Visitor extends ExprBaseVisitor<Void> {
    private ArrayDeque<Matcher> expressions = new ArrayDeque<>(Arrays.asList(Matcher.empty()));

    Matcher expression() {
      Matcher expression = expressions.pop();
      while (!expressions.isEmpty()) {
        expression = expressions.pop().append(expression);
      }
      return expression;
    }

    @Override
    public Void visitAnd(AndContext ctx) {
      Matcher expression = expressions.pop();
      if (expression instanceof And) {
        expressions.push(expression);
      } else {
        expressions.push(and(expression, empty()));
      }
      return super.visitAnd(ctx);
    }

    @Override
    public Void visitClose(CloseContext ctx) {
      Matcher first = expressions.pop();
      expressions.push(expressions.pop().append(first));
      return super.visitClose(ctx);
    }

    @Override
    public Void visitNot(NotContext ctx) {
      expressions.push(not(empty()));
      return super.visitNot(ctx);
    }

    @Override
    public Void visitOpen(OpenContext ctx) {
      expressions.push(and(empty(), empty()));
      return super.visitOpen(ctx);
    }

    @Override
    public Void visitOr(OrContext ctx) {
      expressions.push(or(expressions.pop(), empty()));
      return super.visitOr(ctx);
    }

    @Override
    public Void visitTerm(TermContext ctx) {
      expressions.push(expressions.pop().append(term(ctx.getText())));
      return super.visitTerm(ctx);
    }
  }
}
