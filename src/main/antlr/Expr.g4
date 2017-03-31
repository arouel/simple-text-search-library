grammar Expr;		
prog:	(expr NEWLINE)* ;
and:    AND+ ;
close:  CLOSING_BRACKET ;
not:    NOT ;
open:   OPENING_BRACKET ;
or:     AND* OR AND* ;
term:   TERM ;
expr:	expr and expr
    |	expr or expr
    |	not expr
    |	term
    |	open expr close
    ;
AND : ' ' ;
CLOSING_BRACKET : ')' ;
NOT : '-' ;
OPENING_BRACKET : '(' ;
OR : ',' ;
NEWLINE : [\r\n]+ ;
TERM     : ('0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '\u0100' .. '\u017E')+ ;