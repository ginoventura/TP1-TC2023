package compiladores.errores;

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
        msjSemantico = " " + "- Error semantico -";
        msjSintactico = " " + "- Error sintactico -";
    }

    public void noRetorno(String posicion) {
        System.out.println(msjError + msjLinea + posicion + "; La funcion necesita un return" + msjSemantico);
    }

    public void errorSintactico(String posicion, String msj) {
        System.out.println(msjError + msjLinea + posicion + "; " + msj + msjSintactico);
    }

    public void idRedefinido(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha redefinido" + msjSemantico);
    }

    public void idNoDeclarado(String posicion, String id) {
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' no ha sido declarado" + msjSemantico);
    }

    public void idNoInicializado(String posicion, String id) {
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' no ha sido inicializado" + msjSemantico);
    }

    public void idDiferenteTiposParamArgs(String posicion, String tipo1, String tipo2, String id) {
        System.out.println(msjWarning + msjLinea + posicion + "; El tipo '" + tipo1 + "' del argumento '" + id + "' no coincide con el tipo '" + tipo2 + "' del parametro" + msjSemantico);
    }

    public void idDistintosTipos(String posicion, String tipo1, String tipo2) {
        System.out.println(msjWarning + msjLinea + posicion + "; Esta intentando asociar un '" + tipo1 + "' con un '" + tipo2 + "' " + msjSemantico);
    }
    
   public void funcionNoDeclarada(String posicion, String id){
        System.out.println(msjError + msjLinea + posicion + "; '" + id + "' se ha definido, pero no declarado" + msjSemantico);
    }
}
