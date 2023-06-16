package compiladores.errores;

public class SyntaxErrors {
    
    private int line;
    private int charPosition;
    private String token;

    public SyntaxErrors(int line, int charPosition, String token){
        this.line = line;
        this.charPosition = charPosition;
        this.token = token;
    }

    @Override
    public String toString() {
        return "[" + line + ":" + charPosition + "] - " + token;
    }
}
