grammar Blatt03;

program :  stmt* EOF ;

stmt    :  assign
        |  while
        |  casedef
        ;
while   :  'while'  cond  block ;

assign : ID ':=' expr;

expr    :  expr '*' expr
        |  expr '/' expr
        |  expr '+' expr
        |  expr '-' expr
        |  ID
        |  INT
        |  STRING
        ;


cond    : expr '==' expr
        |  expr '!=' expr
        |  expr '>' expr
        |  expr '<' expr
        ;


casedef : 'if' cond block
        | 'if' cond 'do' stmt* 'else' block
        ;


block   :  'do' stmt* 'end';


//lexer Regeln
ID      :  [a-zA-Z_][a-zA-Z0-9_]* ;
INT     :  [0-9]+ ;
STRING  :  '"' (~[\n\r"])* '"' ;

COMMENT :  '#' ~[\n\r]* -> skip ;
WS      :  [ \t\n\r]+ -> skip ;