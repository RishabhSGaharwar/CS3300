%{
    #include "P1.tab.h"
    int indent = 0;
    int line = 0;
%}
%%
[ \t\r\f]                {;}
";"                      {yylval.string_union = strdup(yytext);    return EOS;}
"//".*"\n"               {line++;}
"/*".*"*/"               {;}
[\n]                     {line++;}
"{"                      {indent++; yylval.string_union = strdup(yytext);    return CURLY_OPEN;}
"}"                      {indent--; yylval.string_union = strdup(yytext);    return CURLY_CLOSE;}
"("                      {yylval.string_union = strdup(yytext);    return PAREN_OPEN;}
")"                      {yylval.string_union = strdup(yytext);    return PAREN_CLOSE;}
"["                      {yylval.string_union = strdup(yytext);    return SQUARE_OPEN;}
"]"                      {yylval.string_union = strdup(yytext);    return SQUARE_CLOSE;}
"return"                 {yylval.string_union = strdup(yytext);    return RETURN;}
"class"                  {yylval.string_union = strdup(yytext);    return CLASS;}
"public"                 {yylval.string_union = strdup(yytext);    return PUBLIC;}
"static"                 {yylval.string_union = strdup(yytext);    return STATIC;}
"void"                   {yylval.string_union = strdup(yytext);    return VOID;}
"main"                   {yylval.string_union = strdup(yytext);    return MAIN;}
"extends"                {yylval.string_union = strdup(yytext);    return EXTEND;}
"System.out.println"     {yylval.string_union = strdup(yytext);    return PRINT_INT;}
"boolean"                {yylval.string_union = strdup(yytext);    return BOOLEAN;}
"int"                    {yylval.string_union = strdup(yytext);    return INT;}
"String"                 {yylval.string_union = strdup(yytext);    return STRING;}
"if"                     {yylval.string_union = strdup(yytext);    return IF;}
"else"                   {yylval.string_union = strdup(yytext);    return ELSE;}
"do"                     {yylval.string_union = strdup(yytext);    return DO;}
"while"                  {yylval.string_union = strdup(yytext);    return WHILE;}
"="                      {yylval.string_union = strdup(yytext);    return ASSIGN;}
"&&"                     {yylval.string_union = strdup(yytext);    return BIN_AND;}
"||"                     {yylval.string_union = strdup(yytext);    return BIN_OR;}
"!="                     {yylval.string_union = strdup(yytext);    return NEQ;}
"<="                     {yylval.string_union = strdup(yytext);    return LEQ;}
"+"                      {yylval.string_union = strdup(yytext);    return ADD;}
"-"                      {yylval.string_union = strdup(yytext);    return SUB;}
"*"                      {yylval.string_union = strdup(yytext);    return MUL;}
"/"                      {yylval.string_union = strdup(yytext);    return DIV;}
".length"                {yylval.string_union = strdup(yytext);    return LENGTH;}
"true"                   {yylval.string_union = strdup(yytext);    return TRUE_VAL;}
"false"                  {yylval.string_union = strdup(yytext);    return FALSE_VAL;}
"this"                   {yylval.string_union = strdup(yytext);    return THIS;}
"new"                    {yylval.string_union = strdup(yytext);    return NEW;}
"!"                      {yylval.string_union = strdup(yytext);    return NOT;}
"#define"                {yylval.string_union = strdup(yytext);    return DEFINE;}
","                      {yylval.string_union = strdup(yytext);    return COMMA;}
"."                      {yylval.string_union = strdup(yytext);    return MEMBER;}
[0-9]+                   {yylval.string_union = strdup(yytext);    return DECIMAL;}
[A-Za-z_][A-Za-z_0-9]*   {yylval.string_union = strdup(yytext);    return ID;}
.                        {yylval.string_union = strdup(yytext);  printf("// Failed to parse macrojava code."); exit(1);}
%%