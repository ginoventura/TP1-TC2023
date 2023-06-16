package compiladores.tablaSimbolos;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import compiladores.errores.CustomErrors;

public class ErrorsListener extends BaseErrorListener {

    private CustomErrors customErrors;

    public ErrorsListener() {
        this.customErrors = new CustomErrors();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object simbolo, int linea, int posicionEnLinea,
            String msj, RecognitionException e) {
        String posicion = "[" + linea + ":" + posicionEnLinea + "]";
        customErrors.errorSintactico(posicion, msj);
    }
}
