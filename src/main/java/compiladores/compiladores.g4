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
IGUAL : '='  ;

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

MAYOR     : '>'  ;
MENOR     : '<'  ;
MAY_IGUAL : '>=' ;
MEN_IGUAL : '<=' ;
EQUAL     : '==' ;
DISTINTO  : '!=' ;

// Bucles
WHILE : 'while' ;
IF    : 'if'    ;
FOR   : 'for'   ;
ELSE  : 'else'  ;

//Regla para los espacios en blanco
WS : [ \n\t\r] -> skip ;

// Numeros
ENTERO  : DIGITO+         ;
DECIMAL : ENTERO'.'ENTERO ;

// Tipos de datos
INT    : 'int'    ; 
DOUBLE : 'double' ;       

RETURN : 'return' ;

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

/* ---------------------------------------------------- */

/*----------------- Bloque de programa -----------------*/

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

bloque : LLA instrucciones LLC ;

instruccion : declaracion PYC
            | asignacion PYC
            | ifor
            | iwhile
            | iif
            | declaracionFuncion PYC
            | definicionFuncion
            | llamadaFuncion PYC
            | retorno PYC
            | bloque
            ;

/* ---------------------------------------------------- */

retorno : RETURN opal;

/* ------ Asignacion y declaracion de variables --------*/

declaracion : tipoDato ID
            | tipoDato ID asign
            ;

asign : IGUAL operacion
      ;

tipoDato : INT
         | DOUBLE
         ;

asignacion  : ID asign
            ;

/* ---------------------------------------------------- */

ifor : FOR PA asignacion PYC operacion PYC ID asign PC instruccion
     ;

iwhile : WHILE PA operacion PC instruccion
       ;

iif : IF PA operacion PC instruccion
    | IF PA operacion PC bloque ELSE bloque
    ;

/* ---------------------------------------------------- */

/*---------------------- Funciones ---------------------*/

declaracionFuncion : tipoDato ID PA parametros PC ;

definicionFuncion : tipoDato ID PA parametros PC bloque ;

parametros : param ;

param : declaracion param 
      | COMA declaracion param
      |
      ;

llamadaFuncion : ID PA argumentos PC ;

argumentos : operacion args ;

args : COMA operacion args 
     |
     ;

operacion : opal ;

opal : disyuncion
     | 
     ;

disyuncion : conjuncion disy ;

disy : OR conjuncion disy
     | 
     ;

conjuncion : comparaciones conj ;

conj : AND comparaciones conj
     | 
     ;

comparaciones : expresion comp ;

comp : opcomp expresion comp 
     | 
     ;

opcomp : EQUAL
       | DISTINTO
       | MAYOR
       | MAY_IGUAL
       | MENOR
       | MEN_IGUAL
       ;

expresion : term exp;

exp : SUMA term exp
    | RESTA term exp
    |
    ;

term : factor t ;

t : MULT factor t
  | DIV factor t
  | MOD factor t
  |
  ;

factor : f PA opal PC
       | f llamadaFuncion
       | f ENTERO
       | f DECIMAL
       | f ID
       ;

f : SUMA
  | RESTA
  | NOT
  |
  ;