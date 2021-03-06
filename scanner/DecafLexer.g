lexer grammar DecafLexer;

@header{
	package compiler.scanner;
}

TYPE:		('int'|'boolean');
ASSIGN_OP:	('='|'+='|'-=');
ARITH_OP_2:	'+'|'-'|'%';
ARITH_OP_1:	'*'|'/';
REL_OP:		'<'|'>'|'<='|'>=';
EQ_OP:		'=='|'!=';
COND_OP_1:	'&&';
COND_OP_2:	'||';
RESERVADA:	(TYPE|'break'|'case'|'callout'|'continue'|'class'|'else'|'false'|'for'|'if'|'int'|'return'|'true'|'void'|'program') ;
HEX_LITERAL:	'0x' HEX_DIGIT (HEX_DIGIT)*;
LITERAL:	INT_LITERAL | CHAR_LITERAL | BOOL_LITERAL;
ID:		ALPHA ALPHA_NUM*;
ALPHA_NUM:	ALPHA | DIGIT;
ALPHA:		('a'..'z'|'A'..'Z'|'_') ;
DIGIT:		('0'..'9');
HEX_DIGIT:	DIGIT|'a'..'f'|'A'..'F';
INT_LITERAL:	DECIMAL_LITERAL|HEX_LITERAL|'-'DECIMAL_LITERAL;
DECIMAL_LITERAL:(DIGIT)+;
BOOL_LITERAL:	'true'|'false';
CHAR_LITERAL:	'\'' CHAR '\'';
STRING_LITERAL:	'"' CHAR* '"';
CHAR:		('a'..'z'|'A'..'Z'|DIGIT) ;
// lo que inventamos

CLASE:		('class');
SIMBOLOS:	('!'|'¡'|'?'|'¿'|'$'|'%'|'&');
D_DIAG:		'//';
DIAG_INV:	'\\';
PUNTO:		'.';
COMILLAS:	'"';
COMILLA:	'\'';
A_LLAVE:	('{');
C_LLAVE:	('}');
A_PARENTESIS:	('(');
C_PARENTESIS:	(')');
A_CORCHETE:	('[');
C_CORCHETE:	(']');
COMA:		(',');
PYCOMA:		(';');
IGUAL:		('=');
MENOS:		('-');
ADMIRACION:	('!');

WS:('\t'|'\n'|'\r'|' '|'0x')+ { skip();};
