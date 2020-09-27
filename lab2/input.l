%option noyywrap yylineno
%{
#include <stdio.h>
#include "y.tab.h"
int ch;
extern void yyerror (char *);
%}

digit [XVI]
letter [a-zA-Z]
cmp [<>=]
divide [();,]
ws [\t\n\r ]
%%
while {ch += yyleng; return WHILE;}
done {ch += yyleng; return DONE;}
":=" {ch += yyleng; return ASSIGN;}
{letter}({letter})* {
   ch += yyleng; return ID;
}
[+-]?{digit}({digit})* {
   ch += yyleng; return NUMBER;
}
{cmp} {ch += yyleng; return CMP;}
{divide} {ch += yyleng; return *yytext;}
{ws}+ {ch += yyleng;}
. {yyerror("Unknown character"); ch += yyleng;}
%%
