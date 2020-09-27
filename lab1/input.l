%{
 #include "y.tab.h"
%}

digit [XVI]
letter [a-zA-Z]

%%
";"   {return SEMICOLON;}
":="  {return ASSIGN;}
"+"   {return PLUS;}
"-"   {return MINUS;}
"*"   {return MULTI;}
"/"   {return DIV;}
"("   {return LPAR;}
")"   {return RPAR;}
">"   {return MORE;}
"<"   {return LESS;}
">="  {return MOREOREQ;}
"<="  {return LESSOREQ;}
"="   {return EQUAL;}

{digit}({digit})* {
   return NUMBER;
}


{letter}({letter})* {
   return IDENT;
}

[\t\n\r ] 
.                   {
    printf("Unknown character [%c]\n", yytext[0]);
    return UNKNOWN;
}

%%

int main(int argc, char **argv) {
 if(argc <2) {
  printf("\nNot enough arguments. Please specify filename.\n");
}

if((yyin = fopen(argv[1], "r")) == NULL) {
 printf("\nCannot open file");
 return -1;
}

yylineno = 1;
while (1) {
 int token = yylex(); 
 if(token == 0)
  break;
 printf("%d\n", token);
}
fclose(yyin);
return 0;
}
