%{
#include <stdio.h>
extern FILE *yyin;
extern int yylineno;
extern int ch;
extern char *yytext;
void yyerror(char *);
%}
%token DONE ID NUMBER ASSIGN WHILE
%right ASSIGN
%left CMP
%%
program: expression | program expression {printf("\nprogram\n");}
expression: WHILE '('statement')' statement DONE';' {printf("\nexpression\n");}
statement: ID ASSIGN value {printf("\nassigment\n");}
statement: st_cmp
st_cmp: value CMP value {printf("\ncomparision\n");}
value: ID {printf("\nidentifier\n");}
value: NUMBER {printf("\nnumber\n");} 
%%

void yyerror(char *msg) {
fprintf(stderr, "%s (%d, %d): %s\n", msg, yylineno, ch, yytext);
}

int main(int argc, char **argv) {
if(argc < 2) {
printf("\nNot enough arguments. Please specify filename. \n");
return -1;
}
if((yyin = fopen(argv[1], "r")) == NULL) {
printf("\nCannot open file %s.\n", argv[1]);
return -1;
}
ch = 1;
yylineno = 1;
yyparse();
return 0;
}
