grammar Expr;



//==========================================================
// Parser Rules
//==========================================================

and             : AND+ ;
close           : CLOSING_BRACKET ;
not             : NOT ;
open            : OPENING_BRACKET ;
or              : AND* OR AND* ;
expr            : expr and expr
                | expr or expr
                | not expr
                | phrase
                | word
                | open expr close
                ;
phrase          : QUOTE ~QUOTE* QUOTE ;
prog            : (expr NEWLINE)* ;
word            : TERM ;



//==========================================================
// Lexer Rules
//==========================================================

AND             : ' ' ;
CLOSING_BRACKET : ')' ;
NEWLINE         : [\r\n]+ ;
NOT             : '-' ;
OPENING_BRACKET : '(' ;
OR              : ',' ;
PUNCTUATION     : ',' | '.' | '?' | '!' | ':' | ';' | '-' | '\'' | '/' | '\\' | '[' | ']' | '{' | '}' | '@' | '#' | '*' | '&' ;
QUOTE           : '"' ;
TERM            : ('0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '\u0100' .. '\u017E')+ ;