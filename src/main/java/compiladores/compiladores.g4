grammar compiladores;

// @header {
// package compiladores;

// }

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

COMA  : ','  ;
PYC   : ';'  ;
PA    : '('  ;
PC    : ')'  ;
LLA   : '{'  ;
LLC   : '}'  ;
CA    : '['  ;
CC    : ']'  ;
ASIGN : '='  ;
SUMA  : '+'  ;
RESTA : '-'  ;
MULT  : '*'  ;
DIV   : '/'  ;
MOD   : '%'  ;
NOT   : '!'  ;
AND   : '&&' ;
OR    : '||' ;
INCR  : '++' ;
DECR  : '--' ;
INCRI : '+=' ;
DECRI : '-=' ;
COMPARADOR: '==' | '!=' | '>' | '>=' | '<' | '<='  ;

// Bucles
WHILE : 'while' ;
IF    : 'if'    ;
FOR   : 'for'   ;

//Regla para los espacios en blanco
WS : [ \n\t\r] -> skip ;

// Numeros
NUMERO : DIGITO+ ;

// Tipos de datos
TIPO : 'int' | 'double' | 'float' ;                  

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

/* ---------------------------------------------------- */

/*----------------- Bloque de programa -----------------*/

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : asignacion
            | declaracion
            | bloque
            | iwhile
            | iif
            | ifor
            | prototipoFuncion
            | declaracionFuncion
            | llamadaFuncion
            ;

bloque : LLA instrucciones LLC ;

/* ---------------------------------------------------- */

/* ------ Asignacion y declaracion de variables --------*/

asignacion : ID ASIGN expresion PYC;

declaracion : TIPO ID inicializacion listaid PYC ;

inicializacion : ASIGN NUMERO
               |
               ;

listaid : COMA ID inicializacion listaid
        |
        ;

/* ---------------------------------------------------- */

/* -------------------- Expresiones --------------------*/

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

/* ---------------------------------------------------- */

/*------------------ Bucle while e if ------------------*/

iwhile : WHILE PA comparacion listacomp PC (bloque|instruccion);

iif : IF PA comparacion listacomp PC (bloque|instruccion) ;

comparacion : PA (ID|NUMERO) COMPARADOR (ID|NUMERO) PC
            | (ID|NUMERO) COMPARADOR (ID|NUMERO)
            | NOT (ID|NUMERO)
            | (ID|NUMERO)
            ;

listacomp : AND comparacion listacomp
          | OR comparacion listacomp
          |
          ;

/* ---------------------------------------------------- */

/*---------------------- Bucle for ---------------------*/

ifor : FOR PA declaracionFor PYC condicionFor PYC incrementoFor PC (bloque|instruccion) ;

declaracionFor : TIPO ID inicializacionFor listaidFor ;

inicializacionFor : ASIGN NUMERO
                  |
                  ;

listaidFor : COMA ID inicializacionFor listaidFor
           |
           ;

condicionFor : (ID|NUMERO) COMPARADOR (ID|NUMERO) ;

incrementoFor : ID INCR listaIncrFor
              | ID DECR listaIncrFor
              | ID INCRI NUMERO listaIncrFor
              | ID DECRI NUMERO listaIncrFor
              |
              ;

listaIncrFor : COMA incrementoFor listaIncrFor 
             |
             ;

/* ---------------------------------------------------- */

/*----------------- Prototipo de funcion ---------------*/

prototipoFuncion : TIPO ID PA parametrosProt PC PYC
                 ;

parametrosProt : TIPO ID listaParamProt 
               | TIPO listaParamProt
               |
               ;

listaParamProt : COMA TIPO listaParamProt
               | COMA TIPO ID listaParamProt
               |
               ;

/* ---------------------------------------------------- */

/* ---------- Implementacion de funciones --------------*/

declaracionFuncion : TIPO ID PA parametrosDecl PC bloque;

parametrosDecl : TIPO ID listaParamDecl
               | 
               ;

listaParamDecl : COMA TIPO ID listaParamDecl 
               |
               ;

/* ---------------------------------------------------- */

/* -------------- Llamadas a funciones -----------------*/

llamadaFuncion : ID PA parametrosLlam PC PYC ;

parametrosLlam : ID listaParamLlam 
               |
               ;

listaParamLlam : COMA ID listaParamLlam 
               |
               ;

/* ---------------------------------------------------- */