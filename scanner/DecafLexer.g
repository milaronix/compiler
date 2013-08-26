lexer grammar DecafLexer;

@header{
	package compiler.scanner;
}


TYPE:		('int'|'boolean');
ASSIGN_OP:	('='|'+='|'-=');

ARITH_OP:	'+'|'-'|'*'|'/'|'%';
REL_OP:		'<'|'>'|'<='|'>=';
EQ_OP:		'=='|'!=';
COND_OP:	'&&'|'||';

RESERVADA:	TYPE|'break'|'case'|'callout'|'continue'|'class'|'else'|'false'|'for'|'if'|'int'|'return'|'true'|'void';

LITERAL:	INT_LITERAL | CHAR_LITERAL | BOOL_LITERAL;
ID:		ALPHA ALPHA_NUM* {System.out.println('ID');};
ALPHA_NUM:	ALPHA | DIGIT;
ALPHA:		('a'..'z'|'A'..'Z'|'_') {System.out.println('ALPHA');};
DIGIT:		'0'..'9';
HEX_DIGIT:	DIGIT|'a'..'f'|'A'..'F';
INT_LITERAL:	DECIMAL_LITERAL|HEX_LITERAL;
DECIMAL_LITERAL:DIGIT (DIGIT)*;
HEX_LITERAL:	'0x' HEX_DIGIT (HEX_DIGIT)*;
BOOL_LITERAL:	'true'|'false';
CHAR_LITERAL:	'/'' CHAR '/'';
STRING_LITERAL:	'"' CHAR* '"';
CHAR:		('a'..'z'|'A'..'Z'|DIGIT) {System.out.println('CHAR');};

// lo que inventamos

CLASE:		('class');


SIMBOLOS:	('!'|'¡'|'?'|'¿'|'$'|'%'|'&') {System.out.println('SIMBOLOS');};
DIAG_INV:	'\\'{System.out.println('DIAG_INV');};
PUNTO:		'.'{System.out.println('PUNTO');};
COMILLAS:	'"'{System.out.println('COMILLAS');};
A_LLAVE:	('{'){System.out.println('A_LLAVE');};
C_LLAVE:	('}'){System.out.println('C_LLAVE');};
A_PARENTESIS:	('('){System.out.println('A_PARENTESIS');};
C_PARENTESIS:	(')'){System.out.println('C_PARENTESIS');};
A_CORCHETE:	('['){System.out.println('A_CORCHETE');};
C_CORCHETE:	(']'){System.out.println('C_CORCHETE');};
COMA:		(','){System.out.println('COMA');};
PYCOMA:		(';'){System.out.println('PYCOMA');};
IGUAL:		('=');
MENOS:		('-');
ADMIRACION:	('!');

WS:('\t'|'\n'|'\r'|' ')+ { skip();};
