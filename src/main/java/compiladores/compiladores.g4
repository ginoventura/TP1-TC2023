grammar compiladores;

// @header {
// package compiladores;

// }

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

PYC   : ';'  ;
PA    : '('  ;
PC    : ')'  ;
LLA   : '{'  ;
LLC   : '}'  ;
CA    : '['  ;
CC    : ']'  ;
ASIGN : '='  ;
COMA  : ','  ;
SUMA  : '+'  ;
RESTA : '-'  ;
MULT  : '*'  ;
DIV   : '/'  ;
MOD   : '%'  ;
NOT   : '!'  ;
OR    : '||' ;
AND   : '&&' ;
COMPARADOR: '==' | '!=' | '>' | '>=' | '<' | '<='  ;

// Bucles
WHILE : 'while' ;

//Regla para los espacios en blanco
WS : [ \n\t\r] -> skip ;

// Numeros
NUMERO : DIGITO+ ;

// Tipos de datos
INT :    'int' ;   
DOUBLE : 'double' ;                                   

OTRO : . ;

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : asignacion
            | declaracion
            | bloque
            | iwhile
            ;

bloque : LLA instrucciones LLC ;

asignacion : ID ASIGN expresion PYC;

declaracion : INT ID inicializacion listaid PYC ;

inicializacion : ASIGN expresion
               |
               ;

listaid : COMA ID inicializacion listaid
        |
        ;

expresion : termino exp ;

exp : SUMA  termino exp
    | RESTA termino exp
    |
    ;

termino : factor term ;

term : MULT factor term
     | DIV  factor term
     | MOD  factor term
     |
     ;

factor : NUMERO
       | ID
       | PA expresion PC 
       ;

iwhile : WHILE PA comparacion PC (bloque|instruccion);

comparacion : (NUMERO|ID) COMPARADOR (NUMERO|ID) ;

/*
---------------------- MI CODIGO ----------------------

// Tokens o reglas gramaticales: minuscula
// Reglas lexicas: MAYUSCULA

// Analisis sintactico descendente:
// match y derivar

// Analisis sintactico ascendente:
// desplazar y reducir

// si : s 
//    | EOF
//    ;

// // PA (Abre parentesis) PC (Cierra parentesis)
// s : PA s PC s 
//   | LLA s LLC s
//   | CA s CC s
//   |
//   ;

// Un programa es un conjunto de instrucciones hasta final de archivo
programa : instrucciones EOF ;

// las instrucciones 
instrucciones : instruccion instrucciones
              |   
              ;

// las instrucciones son:
instruccion : asignacion 
            | declaracion
            | bloque
            | iwhile
            ;

// Bloque de codigo
bloque : LLA instrucciones LLC ;

// Una asignacion es:
asignacion : ID ASIGN (NUMERO|ID) PYC ;

// Una declaracion es:
// una variable de tal tipo que se llama asi:
// puede ser inicializada o puede ser un listado de variables
declaracion : (INT|DOUBLE) ID inicializacion listaid PYC ;

// una inicializacion
inicializacion : ASIGN NUMERO 
               |    
               ;

// lista de valores
listaid : COMA ID inicializacion listaid
        |
        ;

/*
expresion : termino exp ;

exp : SUMA  termino exp 
    | RESTA termino exp 
    |
    ;

termino : factor term ;

term : MULT factor term
     | DIV  factor term
     | MOD  factor term
     | 
     ;

// con los parentesis se resetea la prioridad 
factor : NUMERO 
       | ID
       | PA expresion PC
       ;

iwhile : WHILE PA comparacion PC (bloque|instruccion);

comparacion : (NUMERO|ID) COMPARADOR (NUMERO|ID) ;

 */
