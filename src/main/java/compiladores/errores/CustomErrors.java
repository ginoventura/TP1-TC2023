package compiladores.errores;

import java.util.ArrayList;

public class CustomErrors {
    private String msjError;
    private String msjWarning;
    private String msjLinea;
    private String msjSemantico;
    private String msjSintactico;

    public CustomErrors() {
        msjError = "Error: ";
        msjWarning = "Warning: ";
        msjLinea = "linea: ";
        msjSemantico = " " + "(Error semantico)";
        msjSintactico = " " + "(Error sintactico)";
    }

    public void numeroParametrosIncorrecto(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; Numero de parametros incorrecto" + msjSemantico);
    }

    public void noRetorno(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; La funcion necesita un return" + msjSemantico);
    }

    public void retornoFuncionVoid(String posicion){
        System.out.println(msjError + msjLinea + posicion + ";   Una funcion void no retorna valores" + msjSemantico);
    }

    public void declaradoComoVariable(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha declarado como variable" + msjSemantico);
    }

    public void funcionVoid(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' retorna vacio" + msjSemantico);
    }

    public void funcionNoDeclarada(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha definido, pero no declarado" + msjSemantico);
    }
    public void cantidadParamArgsDistinta(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; El numero de parametros es distinto al numero de argumentos" + msjSemantico);
    }

    public void retornoDistintoTipo(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; Estas retornando un tipo de dato distinto" + msjSemantico);
    }

    public void ordenPrototipo(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; El tipo o el orden no coincide con el prototipo." + msjSemantico);
    }

    public void idNoUsado(ArrayList<String> notUsed){
        for (String id : notUsed) {
            System.out.println(msjWarning + "'" + id + "' se ha declarado pero no usado" + msjSemantico);
        }
    }

    public void idRedefinido(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha redefinido" + msjSemantico);
    }

    public void idDeclarado(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha declarado en este contexto" + msjSemantico);
    }

    public void idNoDeclarado(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' no ha sido declarado" + msjSemantico);
    }

    public void idDistintosTipos(String posicion, String tipo1, String tipo2){
        System.out.println(msjWarning + msjLinea + posicion + "; Esta intentando vincular un '" + tipo1 + "' con un '" + tipo2 + msjSemantico);
    }

    public void errorSintactico(String posicion, String msg){
        System.out.println(msjError + msjLinea + posicion + "; " + msg + msjSintactico);
    }

    public void idNoInicializado(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' no ha sido inicializado" + msjSemantico);
    }

    public void prototipoContexto(String posicion){
        System.out.println(msjError + msjLinea + posicion + "; El prototipo debe estar en el contexto global" + msjSemantico);
    }

    public void idDiferenteTipoRetorno(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' es diferente del tipo de retorno" + msjSemantico);
    }

    public void idDiferenteTiposParamArgs(String posicion, String tipo1, String tipo2, String id){
        System.out.println(msjWarning + msjLinea + posicion + "; El tipo '" + tipo1 + "' del argumento '" + id + "' no coincide el tipo '" + tipo2 + "' del parametro" + msjSemantico);
    }
}
