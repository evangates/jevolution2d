package jevolution.expressions;

import java_cup.runtime.*;

%%

%cup
%unicode
%line
%column

%class Scanner
%implements sym
%{

  private Symbol symbol(int sym) {
    return new Symbol(sym, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int sym, Object val) {
    return new Symbol(sym, yyline+1, yycolumn+1, val);
  }

  private void error(String message) {
    System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }
%}

int = [0-9]+

exponent_part = [\e\E][\+\-]?{int}

float = {int}\.{int}{exponent_part}? | \.{int}{exponent_part}? | {int}{exponent_part}

literal = 0 | {int} | {float}

whitespace = [ \t]

%%

/* properties */
"red"					{ return symbol(RED); }
"green"					{ return symbol(GREEN); }
"blue"					{ return symbol(BLUE); }

"width"					{ return symbol(WIDTH); }
"height"				{ return symbol(HEIGHT); }

"velocity"				{ return symbol(VELOCITY); }
"acceleration"			{ return symbol(ACCELERATION); }
"angularVelocity"		{ return symbol(ANGULAR_VELOCITY); }

/* literals */
{literal}				{ return symbol(LITERAL, yytext()); }

/* parens */
"("						{ return symbol(LPAREN); }
")"						{ return symbol(RPAREN); }

/* operators */
"+"						{ return symbol(ADDOP); }
"-"						{ return symbol(SUBOP); }
"*"						{ return symbol(MULTOP); }
"/"						{ return symbol(DIVOP); }
"^"						{ return symbol(POWOP); }

{whitespace}			{ /* ignore */ }

/* error fallback */
.|\n					{ error("Illegal character <" + yytext() + ">"); return symbol(ERROR, yytext()); }
