grammar compiladores;

@header {
package compiladores;

}

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
ELSE  : 'else'  ;

RETURN: 'return';

//Regla para los espacios en blanco
WS : [ \n\t\r] -> skip ;

// Numeros
ENTERO  : DIGITO+         ;
DECIMAL : ENTERO'.'ENTERO ;

// Tipos de datos
INT    : 'int'    ; 
DOUBLE : 'double' ;                  

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

/* ---------------------------------------------------- */

/*----------------- Bloque de programa -----------------*/

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : declaracion
            | asignacion
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

declaracion : tipoDato ID inicializacion listaid PYC ;

asignacion : ID ASIGN expresion PYC ;

inicializacion : ASIGN (ENTERO|DECIMAL)
               |
               ;

listaid : COMA ID inicializacion listaid
        |
        ;

tipoDato : INT 
         | DOUBLE
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

factor : (ENTERO|DECIMAL)
       | ID
       | PA expresion PC 
       ;

/* ---------------------------------------------------- */

/*------------------ Bucle while e if ------------------*/

iwhile : WHILE PA comparacion listacomp PC (bloque|instruccion);

iif : IF PA comparacion listacomp PC (bloque|instruccion) 
    | IF PA comparacion listacomp PC bloque ELSE bloque;

comparacion : PA (ID|(ENTERO|DECIMAL)) COMPARADOR (ID|(ENTERO|DECIMAL)) PC
            | (ID|(ENTERO|DECIMAL)) COMPARADOR (ID|(ENTERO|DECIMAL))
            | NOT (ID|(ENTERO|DECIMAL))
            | (ID|(ENTERO|DECIMAL))
            ;

listacomp : AND comparacion listacomp
          | OR comparacion listacomp
          |
          ;

/* ---------------------------------------------------- */

/*---------------------- Bucle for ---------------------*/

ifor : FOR PA declaracionFor PYC condicionFor PYC incrementoFor PC (bloque|instruccion) ;

declaracionFor : tipoDato ID inicializacionFor listaidFor ;

inicializacionFor : ASIGN (ENTERO|DECIMAL)
                  |
                  ;

listaidFor : COMA ID inicializacionFor listaidFor
           |
           ;

condicionFor : (ID|(ENTERO|DECIMAL)) COMPARADOR (ID|(ENTERO|DECIMAL)) ;

incrementoFor : ID INCR listaIncrFor
              | ID DECR listaIncrFor
              | ID INCRI (ENTERO|DECIMAL) listaIncrFor
              | ID DECRI (ENTERO|DECIMAL) listaIncrFor
              |
              ;

listaIncrFor : COMA incrementoFor listaIncrFor 
             |
             ;

/* ---------------------------------------------------- */

/*----------------- Prototipo de funcion ---------------*/

prototipoFuncion : tipoDato ID PA parametrosProt PC PYC
                 ;

parametrosProt : tipoDato ID listaParamProt 
               | tipoDato listaParamProt
               |
               ;

listaParamProt : COMA tipoDato listaParamProt
               | COMA tipoDato ID listaParamProt
               |
               ;

/* ---------------------------------------------------- */

/* ---------- Implementacion de funciones --------------*/

declaracionFuncion : tipoDato ID PA parametrosDecl PC bloque;

parametrosDecl : tipoDato ID listaParamDecl
               | 
               ;

listaParamDecl : COMA tipoDato ID listaParamDecl 
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