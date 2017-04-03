grammar Expr;		
prog  :	(expr NEWLINE)* ;
and   : AND+ ;
close : CLOSING_BRACKET ;
not   : NOT ;
open  : OPENING_BRACKET ;
or    : AND* OR AND* ;
term  : TERM
      | QUOTE ~QUOTE* QUOTE
      ;
expr  : expr and expr
      | expr or expr
      | not expr
      | term
      | open expr close
      ;
AND             : ' ' ;
CLOSING_BRACKET : ')' ;
NEWLINE         : [\r\n]+ ;
NOT             : '-' ;
OPENING_BRACKET : '(' ;
OR              : ',' ;
PUNCTUATION     : ',' | '.' | '?' | '!' | ':' | ';' | '-' | '\'' | '/' | '\\' | '[' | ']' | '{' | '}' | '@' | '#' | '*' | '&' ;
QUOTE           : '"' ;
TERM            : ('0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '\u0100' .. '\u017E')+ ;