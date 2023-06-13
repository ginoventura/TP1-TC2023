package compiladores.tablaSimbolos;

public abstract class Id {
    
    private boolean inicializado;
    private boolean usado;
    private String tipoDato;
    private String tokenNombre;

    public Id() {
    
    }

    public Id(boolean inicializado, boolean usado, String tipoDato, String tokenNombre) {
        this.inicializado = inicializado;
        this.usado = usado;
        this.tipoDato = tipoDato;
        this.tokenNombre = tokenNombre;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTokenNombre() {
        return tokenNombre;
    }

    public void setTokenNombre(String tokenNombre) {
        this.tokenNombre = tokenNombre;
    }

    @Override
    public String toString() {
        return String.valueOf(this.isInicializado());
    }   
}
