package compiladores.errores;

public class SyntaxErrors {
    
    private int linea;
    private int charPosicion;
    private String token;

    public SyntaxErrors(int linea, int charPosicion, String token) {
        this.linea = linea;
        this.charPosicion = charPosicion;
        this.token = token;    
    }

    @Override
    public String toString() {
        return "[" + linea + ":" + charPosicion + "] - " + token;
    }
}
