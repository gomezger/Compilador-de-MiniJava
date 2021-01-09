//probar si lee bien los char de tipo 'a' '\a' '\n' '\t', '\\'
// y que tire erro al leer 'aa'

char a = 'a';
char a = '\a'; //token con lexema a 
char a = '\n'; //token con lexema salto de linea
char a = '\t'; //token con lexema tab
char a = '\\'; // token con lexema barra
char a = 'aa'; // error por contener dos caracteres