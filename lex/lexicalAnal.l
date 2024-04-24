%{
int ke = 0, op = 0, va = 0, co = 0, se = 0, ch = 0;
%}

%%
int|float|const { printf("Keyword: %s\n", yytext); ke++; }
[+*\/=%-] { printf("Operator: %s\n", yytext); op++; }

[0-9]+(\.[0-9]+)? { printf("Number: %s\n", yytext); co++; }
[a-zA-Z][a-zA-Z0-9]* { printf("Variable: %s\n", yytext); va++; }
[;,:](\s)* { printf("Separator: %s\n", yytext); se++; }

%%

int main()
{
    printf("\nENTER THE SEQUENCE\n");
    yylex();
    printf("Number of keywords: %d\n", ke);
    printf("Number of operators: %d\n", op);
    printf("Number of variables: %d\n", va);
    printf("Number of numbers: %d\n", co);
    printf("Number of separators: %d\n", se);
    ch = ke + op + va + co + se;
    printf("Total Number of tokens: %d\n", ch);
    return 0;
}