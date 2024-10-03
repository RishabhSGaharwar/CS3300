%{
    #include <stdio.h>
    #include <string.h>
    #include <stdlib.h>
    int yylex();
    int yyerror(const char*);

    char * concat2(char *a, char *b)
    {
        int aLen = strlen(a);
        int bLen = strlen(b);
        char *ret = (char *)(malloc(sizeof(char)*(aLen+bLen+2)));
        strcpy(ret, a);
        strcat(ret, " ");
        strcat(ret, b);
        ret[aLen+bLen+1] = '\0';
        return ret;
    }
    char * concat3(char *a, char*b, char *c)
    {
        return concat2(a, concat2(b, c));
    }
    char * concat4(char *a, char *b, char *c, char *d)
    {
        return concat2(concat2(a, b), concat2(c, d));
    }
    void beautify(char *code)
    {
        int sz = strlen(code);
        int indent = 0;
        for(int i=0; i<sz; i++)
        {
            if(code[i]==';')
            {
                printf("%c %s", code[i], "\n");
                for(int j=0; j<indent; j++)
                {
                    printf("%c", '\t');
                }
                i++;
            }
            else if(code[i]=='{')
            {
                printf("%c %s", code[i], "\n");
                indent++;
                for(int j=0; j<indent; j++)
                {
                    printf("%c", '\t');
                }
                i++;
            }
            else if(code[i]=='}')
            {
                printf("%c %s", code[i], "\n");
                indent--;
                for(int j=0; j<indent; j++)
                {
                    printf("%c", '\t');
                }
                i++;
            }
            else
            {
                printf("%c", code[i]);
            }
        }
    }
    struct macroStruct
    {
        int argCount;
        char *name;
        char **arg;
        char *body;
    };
    struct macroSubstitute
    {
        int argCount;
        char *name;
        char **arg;
        char *body;
        struct macroStruct* definition;
    };
    struct NodeMacroExp
    {
        struct macroStruct *node;
        struct NodeMacroExp* next;
    };
    struct NodeMacroSt
    {
        struct macroStruct *node;
        struct NodeMacroSt* next;
    };
    struct NodeMacroExp* headExp = NULL; struct NodeMacroExp* tailExp = NULL;
    struct NodeMacroSt* headSt = NULL; struct NodeMacroSt* tailSt = NULL;

    struct NodeMacroExp* checkMacroExp(char *name)
    {
        if(headExp == NULL) return NULL;
        struct NodeMacroExp* cur = headExp;
        while(cur!=NULL)
        {
            if(strcmp(name, cur->node->name)==0)
            {
                return cur;
            }
            else
            {
                cur = cur->next;
            }
        }
        return NULL;
    }

    struct NodeMacroSt* checkMacroSt(char *name)
    {
        if(headSt == NULL) return NULL;
        struct NodeMacroSt* cur = headSt;
        while(cur!=NULL)
        {
            if(strcmp(name, cur->node->name)==0)
            {
                return cur;
            }
            else
            {
                cur = cur->next;
            }
        }
        return NULL;
    }

    void addMacroExp(char *macroname, int args, char *body, char *macro)
    {
        struct NodeMacroExp *newNode = (struct NodeMacroExp *)(malloc(sizeof(struct NodeMacroExp)));
        newNode->next = NULL;
        newNode->node = (struct macroStruct *)(malloc(sizeof(struct macroStruct)));
        newNode->node->name = strdup(macroname);
        if(args==0)
        {
            newNode->node->argCount = 0;
            newNode->node->arg = NULL;
            newNode->node->body = strdup(body);
        }
        else if(args == 1)
        {
            newNode->node->argCount = 1;
            newNode->node->arg = (char **)(malloc(sizeof(char *)));
            int k = 0; int start = -1; int end = -1;
            while(macro[k]!='(')
            {
                k++;
            }
            k++;
            k++;
            start = k;
            while(macro[k]!=' ')
            {
                k++;
            }
            end = k;
            char *argname = (char *)(malloc(sizeof(char)*(end-start+1)));
            for(int i=start; i<end; i++)
            {
                argname[i-start] = macro[i];
            }
            newNode->node->arg[0] = strdup(argname);
            newNode->node->arg[0][end-start] = '\0';
            free(argname);
            newNode->node->body = strdup(body);
        }
        else
        {
            int k = 0;
            int cnt = 0;
            int start = 0;
            int end = -1;
            while(macro[k]!='(') {k++;}
            k++;
            k++;
            start = k;
            while(macro[k]!=')')
            {
                if(macro[k]==','){
                    cnt++;
                }
                k++;
            }
            end = k-1;
            cnt++;
            newNode->node->argCount = cnt;
            newNode->node->arg = (char **)(malloc(sizeof(char *)*(cnt)));
            for(int i=0; i<cnt; i++)
            {
                k = start;
                while(macro[k]!=' ')
                {
                    k++;
                }
                end = k;
                char *argname = (char *)(malloc(sizeof(char)*(end-start+1)));
                for(int i=start; i<end; i++)
                {
                    argname[i-start] = macro[i];
                }
                newNode->node->arg[i] = strdup(argname);
                newNode->node->arg[i][end-start] = '\0';
                free(argname);
                start = end+3;
            }
            newNode->node->body = strdup(body);
        }
        if(headExp==NULL)
        {
            headExp = newNode;
            tailExp = newNode;
        }
        else
        {
            tailExp->next = newNode;
            tailExp = newNode;
        }
    }
    void addMacroSt(char *macroname, int args, char *body, char *macro)
    {
        struct NodeMacroSt *newNode = (struct NodeMacroSt *)(malloc(sizeof(struct NodeMacroSt)));
        newNode->next = NULL;
        newNode->node = (struct macroStruct *)(malloc(sizeof(struct macroStruct)));
        newNode->node->name = strdup(macroname);
        if(args==0)
        {
            newNode->node->argCount = 0;
            newNode->node->arg = NULL;
            newNode->node->body = strdup(body);
        }
        else if(args == 1)
        {
            newNode->node->argCount = 1;
            newNode->node->arg = (char **)(malloc(sizeof(char *)));
            int k = 0; int start = -1; int end = -1;
            while(macro[k]!='(')
            {
                k++;
            }
            k++;
            k++;
            start = k;
            while(macro[k]!=' ')
            {
                k++;
            }
            end = k;
            char *argname = (char *)(malloc(sizeof(char)*(end-start+1)));
            for(int i=start; i<end; i++)
            {
                argname[i-start] = macro[i];
            }
            newNode->node->arg[0] = strdup(argname);
            newNode->node->arg[0][end-start] = '\0';
            free(argname);
            newNode->node->body = strdup(body);
        }
        else if(args==-1)
        {
            int k = 0;
            int cnt = 0;
            int start = 0;
            int end = -1;
            while(macro[k]!='(') {k++;}
            k++;
            k++;
            start = k;
            while(macro[k]!=')')
            {
                if(macro[k]==','){
                    cnt++;
                }
                k++;
            }
            end = k-1;
            cnt++;
            newNode->node->argCount = cnt;
            newNode->node->arg = (char **)(malloc(sizeof(char *)*(cnt)));
            for(int i=0; i<cnt; i++)
            {
                k = start;
                while(macro[k]!=' ')
                {
                    k++;
                }
                end = k;
                char *argname = (char *)(malloc(sizeof(char)*(end-start+1)));
                for(int i=start; i<end; i++)
                {
                    argname[i-start] = macro[i];
                }
                newNode->node->arg[i] = strdup(argname);
                newNode->node->arg[i][end-start] = '\0';
                free(argname);
                start = end+3;
            }
            newNode->node->body = strdup(body);
        }
        if(headSt == NULL)
        {
            headSt = newNode;
            tailSt = newNode;
        }
        else
        {
            tailSt->next = newNode;
            tailSt = newNode;
        }
    }
    char * parseBody(char *args, char **parameter, char *body, int parametercount)
    {
        int argcount = 1;
        for(int i=0; i<strlen(args); i++)
        {
            if(args[i]==',')
            {
                argcount++;
            }
        }
        if(argcount!=parametercount) {yyerror(NULL);}
        char **arguments = (char **)(malloc(sizeof(char*)*parametercount));
        int start = 0, end = 0;
        for(int i=0; i<parametercount; i++)
        {
            int k = start;
            while(k<strlen(args) && args[k]!=',')
            {
                k++;
            }
            end = k;
            char *argname = (char *)(malloc(sizeof(char)*(end-start+1)));
            for(int j=start; j<end; j++)
            {
                argname[j-start] = args[j];
            }
            argname[end-start] = '\0';
            arguments[i] = strdup(argname);
            free(argname);
            start = end+2;
        }
        start = 0, end = -1;
        int sz = 0;
        start=0, end=-1;
        for(int i=0; i<strlen(body); i++)
        {
            if(body[i]==' ')
            {
                end = i;
                int x = end-start+1;
                char *var = (char *)(malloc(sizeof(char)*x));
                for(int j=start; j<end; j++)
                {
                    var[j-start] = body[j];
                }
                var[end-start] = '\0';
                int ii=0;
                for(; ii<parametercount; ii++)
                {
                    if(strcmp(parameter[ii], var)==0)
                    {
                        sz += strlen(arguments[ii]);
                        break;
                    }
                }
                if(ii==parametercount)
                {
                    sz += strlen(var);
                }
                sz++;
                start = end+1;
                free(var);
            }
        }
        if(body[strlen(body)-1]==')')
        {
            sz++;
        }
        char * newbody = (char *)(malloc(sizeof(char)*(sz+1)));
        memset(newbody, '\0', sizeof(newbody));
        start=0, end=-1;
        for(int i=0; i<strlen(body); i++)
        {
            if(body[i]==' ')
            {
                end = i;
                int x = end-start+1;
                char *var = (char *)(malloc(sizeof(char)*x));
                for(int j=start; j<end; j++)
                {
                    var[j-start] = body[j];
                }
                var[end-start] = '\0';
                int ii=0;
                for(; ii<parametercount; ii++)
                {
                    if(strcmp(parameter[ii], var)==0)
                    {
                        strcat(newbody, arguments[ii]);
                        break;
                    }
                }
                if(ii==parametercount)
                {
                    strcat(newbody, var);
                }
                start = end+1;
                free(var);
                strcat(newbody, " ");
            }
        }
        if(body[strlen(body)-1]==')')
        {
            newbody[sz-1] = ')';
        }
        return newbody;
    }
%}

%union
{
    char *string_union;
}

%token<string_union> EOS
%token<string_union> CURLY_OPEN CURLY_CLOSE PAREN_OPEN PAREN_CLOSE SQUARE_OPEN SQUARE_CLOSE
%token<string_union> CLASS PUBLIC STATIC VOID MAIN EXTEND PRINT_INT BOOLEAN INT STRING RETURN
%token<string_union> IF ELSE DO WHILE LENGTH
%token<string_union> TRUE_VAL FALSE_VAL THIS NEW NOT ASSIGN BIN_AND BIN_OR NEQ LEQ ADD SUB MUL DIV
%token<string_union> DEFINE COMMA MEMBER DECIMAL ID
%nonassoc SQUARE_OPEN PAREN_OPEN CURLY_OPEN PRINT_INT 
%left MEMBER LENGTH 
%right ASSIGN EOS
%nonassoc XIF
%nonassoc ELSE
%left ADD SUB
%left MUL DIV
%left BIN_OR
%left BIN_AND
%nonassoc NOT
%nonassoc NEQ LEQ

%type<string_union> MacroDefinitionList
%type<string_union> TypeDeclarationList
%type<string_union> StatementList
%type<string_union> CommaTypeIdentifierList
%type<string_union> TypeIdentifierEOSList
%type<string_union> MethodDeclarationList
%type<string_union> CommaExpressionList
%type<string_union> CommaIdentifierList
%type<string_union> Goal
%type<string_union> MainClass
%type<string_union> TypeDeclaration
%type<string_union> MethodDeclaration
%type<string_union> Type
%type<string_union> Statement
%type<string_union> Expression
%type<string_union> PrimaryExpression
%type<string_union> Boolean_Exp
%type<string_union> MacroDefinition
%type<string_union> MacroDefStatement
%type<string_union> MacroDefExpression
%type<string_union> Identifier
%type<string_union> Integer
%start Goal

%%

//Lists
MacroDefinitionList :     {$$ = "";} | MacroDefinitionList MacroDefinition {$$ = concat2($1, $2);}
;
TypeDeclarationList :     {$$ = "";} | TypeDeclarationList TypeDeclaration {$$ = concat2($1, $2);}
;
StatementList :           {$$ = "";} | Statement StatementList  {$$ = concat2($1, $2);}
;
CommaTypeIdentifierList : COMMA Type Identifier {$$ = concat3($1, $2, $3);} | COMMA Type Identifier CommaTypeIdentifierList {$$ = concat4($1, $2, $3, $4);}
;
TypeIdentifierEOSList :   {$$ = "";} | TypeIdentifierEOSList Type Identifier EOS {$$ = concat4($1, $2, $3, $4);}
;
MethodDeclarationList :   {$$ = "";} | MethodDeclarationList MethodDeclaration {$$ = concat2($1, $2);}
;
CommaExpressionList :     COMMA Expression {$$ = concat2($1, $2);} | COMMA Expression CommaExpressionList {$$ = concat3($1, $2, $3);}
;
CommaIdentifierList :     COMMA Identifier {$$ = concat2($1, $2);} | COMMA Identifier CommaIdentifierList {$$ = concat3($1, $2, $3);}
;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Grammar
Goal : MacroDefinitionList MainClass TypeDeclarationList {$$ = concat2($2, $3); beautify($$);}
;
MainClass : CLASS Identifier 
            CURLY_OPEN PUBLIC STATIC VOID MAIN 
            PAREN_OPEN STRING SQUARE_OPEN SQUARE_CLOSE Identifier 
            PAREN_CLOSE CURLY_OPEN PRINT_INT 
            PAREN_OPEN Expression PAREN_CLOSE 
            EOS CURLY_CLOSE CURLY_CLOSE {$$ = concat2(concat4(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8), concat4($9, $10, $11, $12), concat4($13, $14, $15, $16)), concat2(concat4($17, $18, $19, $20), $21));}
;
TypeDeclaration : CLASS Identifier 
                  CURLY_OPEN TypeIdentifierEOSList MethodDeclarationList CURLY_CLOSE {$$ = concat2(concat3($1, $2, $3), concat3($4, $5, $6));}
                | CLASS Identifier EXTEND Identifier 
                  CURLY_OPEN TypeIdentifierEOSList MethodDeclarationList CURLY_CLOSE {$$ = concat2(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8));}
;
MethodDeclaration : PUBLIC Type Identifier PAREN_OPEN PAREN_CLOSE CURLY_OPEN TypeIdentifierEOSList StatementList RETURN Expression EOS CURLY_CLOSE   {$$ = concat3(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8), concat4($9, $10, $11, $12));}
                  | PUBLIC Type Identifier PAREN_OPEN Type Identifier PAREN_CLOSE CURLY_OPEN TypeIdentifierEOSList StatementList RETURN Expression EOS CURLY_CLOSE   {$$ = concat4(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8), concat4($9, $10, $11, $12), concat2($13, $14));}
                  | PUBLIC Type Identifier PAREN_OPEN Type Identifier CommaTypeIdentifierList PAREN_CLOSE CURLY_OPEN TypeIdentifierEOSList StatementList RETURN Expression EOS CURLY_CLOSE   {$$ = concat4(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8), concat4($9, $10, $11, $12), concat3($13, $14, $15));}
;
Type : INT SQUARE_OPEN SQUARE_CLOSE {$$ = concat3($1, $2, $3);}
     | BOOLEAN {$$ = $1;}
     | INT {$$ = $1;}
     | Identifier {$$ = $1;}
;
Statement : CURLY_OPEN StatementList CURLY_CLOSE {$$ = concat3($1, $2, $3); }
          | PRINT_INT PAREN_OPEN Expression PAREN_CLOSE EOS {$$ = concat2($1, concat4($2, $3, $4, $5));}
          | Identifier ASSIGN Expression EOS {$$ = concat4($1, $2, $3, $4);}
          | Identifier SQUARE_OPEN Expression SQUARE_CLOSE ASSIGN Expression EOS {$$ = concat2(concat3($1, $2, $3), concat4($4, $5, $6, $7));}
          | IF PAREN_OPEN Expression PAREN_CLOSE Statement {$$ = concat2($1, concat4($2, $3, $4, $5));} %prec XIF
          | IF PAREN_OPEN Expression PAREN_CLOSE Statement ELSE Statement {$$ = concat2(concat3($1, $2, $3), concat4($4, $5, $6, $7));}
          | DO Statement WHILE PAREN_OPEN Expression PAREN_CLOSE EOS {$$ = concat2(concat3($1, $2, $3), concat4($4, $5, $6, $7));}
          | WHILE PAREN_OPEN Expression PAREN_CLOSE Statement {$$ = concat2($1, concat4($2, $3, $4, $5));}
          | Identifier PAREN_OPEN PAREN_CLOSE EOS 
            {
                struct NodeMacroSt *nodeSt = checkMacroSt($1);
                if(nodeSt==NULL) yyerror(NULL);
                else if(nodeSt->node->argCount!=0) yyerror(NULL);
                else
                {
                    $$ = nodeSt->node->body;
                }
            }
          | Identifier PAREN_OPEN Expression PAREN_CLOSE EOS 
            {
                struct NodeMacroSt *nodeSt = checkMacroSt($1);
                if(nodeSt==NULL) yyerror(NULL);
                else if(nodeSt->node->argCount!=1) yyerror(NULL);
                else
                {
                    $$ = parseBody($3, nodeSt->node->arg, nodeSt->node->body, nodeSt->node->argCount);
                }
            }
          | Identifier PAREN_OPEN Expression CommaExpressionList PAREN_CLOSE EOS 
            {
                struct NodeMacroSt *nodeSt = checkMacroSt($1);
                if(nodeSt==NULL) yyerror(NULL);
                else
                {
                    $$ = parseBody(concat2($3, $4), nodeSt->node->arg, nodeSt->node->body, nodeSt->node->argCount);
                }
            }
;
Expression : PrimaryExpression BIN_AND PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression BIN_OR PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression NEQ PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression LEQ PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression ADD PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression SUB PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression MUL PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression DIV PrimaryExpression {$$ = concat3($1, $2, $3);}
           | PrimaryExpression SQUARE_OPEN PrimaryExpression SQUARE_CLOSE {$$ = concat4($1, $2, $3, $4);}
           | PrimaryExpression LENGTH {$$ = concat2($1, $2);}
           | PrimaryExpression MEMBER Identifier PAREN_OPEN PAREN_CLOSE {$$ = concat2($1, concat4($2, $3, $4, $5));}
           | PrimaryExpression MEMBER Identifier PAREN_OPEN Expression PAREN_CLOSE {$$ = concat2(concat3($1, $2, $3), concat3($4, $5, $6));}
           | PrimaryExpression MEMBER Identifier PAREN_OPEN Expression CommaExpressionList PAREN_CLOSE {$$ = concat2(concat3($1, $2, $3), concat4($4, $5, $6, $7));}
           | PrimaryExpression {$$ = $1;}
           | Identifier PAREN_OPEN PAREN_CLOSE 
             {
                struct NodeMacroExp *nodeExp = checkMacroExp($1);
                if(nodeExp==NULL) yyerror(NULL);
                else if(nodeExp->node->argCount!=0) yyerror(NULL);
                else
                {
                    $$ = nodeExp->node->body;
                }
             }
           | Identifier PAREN_OPEN Expression PAREN_CLOSE 
             {
                struct NodeMacroExp *nodeExp = checkMacroExp($1);
                if(nodeExp==NULL) yyerror(NULL);
                else if(nodeExp->node->argCount!=1) yyerror(NULL);
                else
                {
                    $$ = parseBody($3, nodeExp->node->arg, nodeExp->node->body, nodeExp->node->argCount);                                                            
                }
             }
           | Identifier PAREN_OPEN Expression CommaExpressionList PAREN_CLOSE 
             {
                struct NodeMacroExp *nodeExp = checkMacroExp($1);
                if(nodeExp==NULL) yyerror(NULL);
                else 
                {
                    $$ = parseBody(concat2($3, $4), nodeExp->node->arg, nodeExp->node->body, nodeExp->node->argCount);
                }
             }
;
PrimaryExpression : Integer {$$ = $1;}
                  | Boolean_Exp {$$ = $1;}
                  | Identifier {$$ = $1;} 
                  | THIS {$$ = $1;}
                  | NEW INT SQUARE_OPEN Expression SQUARE_CLOSE {$$ = concat2(concat4($1, $2, $3, $4), $5);}
                  | NEW Identifier PAREN_OPEN PAREN_CLOSE {$$ = concat4($1, $2, $3, $4);}
                  | NOT Expression {$$ = concat2($1, $2);}
                  | PAREN_OPEN Expression PAREN_CLOSE {$$ = concat3($1, $2, $3);}
;
Boolean_Exp : TRUE_VAL {$$ = $1;}
            | FALSE_VAL {$$ = $1;}
;

MacroDefinition : MacroDefStatement {$$ = $1;}
                | MacroDefExpression {$$ = $1;}
;
MacroDefStatement : DEFINE Identifier PAREN_OPEN PAREN_CLOSE CURLY_OPEN StatementList CURLY_CLOSE 
                    {
                        $$ = concat2(concat4($1, $2, $3, $4), concat3($5, $6, $7));
                        if(checkMacroExp($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroSt($2)==NULL?addMacroSt($2, 0, $6, $$):yyerror(NULL);
                    }
                  | DEFINE Identifier PAREN_OPEN Identifier PAREN_CLOSE CURLY_OPEN StatementList CURLY_CLOSE 
                    {
                        $$ = concat2(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8));
                        if(checkMacroExp($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroSt($2)==NULL?addMacroSt($2, 1, $7, $$):yyerror(NULL);
                    }
                  | DEFINE Identifier PAREN_OPEN Identifier CommaIdentifierList PAREN_CLOSE CURLY_OPEN StatementList CURLY_CLOSE 
                    {
                        $$ = concat3(concat3($1, $2, $3), concat3($4, $5, $6), concat3($7, $8, $9));
                        if(checkMacroExp($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroExp($2)==NULL?addMacroSt($2, -1, $8, $$):yyerror(NULL);
                    }
;
MacroDefExpression : DEFINE Identifier PAREN_OPEN PAREN_CLOSE PAREN_OPEN Expression PAREN_CLOSE 
                    {
                        $$ = concat2(concat3($1, $2, $3), concat4($4, $5, $6, $7));
                        if(checkMacroSt($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroExp($2)==NULL?addMacroExp($2, 0, concat3($5, $6, $7), $$):yyerror(NULL);
                    }
                   | DEFINE Identifier PAREN_OPEN Identifier PAREN_CLOSE PAREN_OPEN Expression PAREN_CLOSE 
                    {
                        $$ = concat2(concat4($1, $2, $3, $4), concat4($5, $6, $7, $8));
                        if(checkMacroSt($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroExp($2)==NULL?addMacroExp($2, 1, concat3($6, $7, $8), $$):yyerror(NULL);
                    }
                   | DEFINE Identifier PAREN_OPEN Identifier CommaIdentifierList PAREN_CLOSE PAREN_OPEN Expression PAREN_CLOSE 
                    {
                        $$ = concat3(concat3($1, $2, $3), concat3($4, $5, $6), concat3($7, $8, $9));
                        if(checkMacroSt($2)!=NULL)
                        {
                            yyerror(NULL);
                        }
                        checkMacroExp($2)==NULL?addMacroExp($2, -1, concat3($7, $8, $9), $$):yyerror(NULL);
                    }
;
Identifier : ID {$$ = $1;}
;
Integer : DECIMAL {$$ = $1;} | SUB DECIMAL {$$ = concat2($1, $2);} | ADD DECIMAL {$$ = concat2($1, $2);}
;
%%

int main(int argc, char **argv)
{
    yyparse();
    return 0;
}

int yywrap()
{
    return 1;
}

int yyerror(const char *s)
{
    printf("// Failed to parse macrojava code.");  
    exit(1);
}