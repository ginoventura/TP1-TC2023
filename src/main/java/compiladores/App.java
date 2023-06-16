package compiladores;

import compiladores.tablaSimbolos.ErrorsListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("TP2 / Arreguez - Ventura");
        // create a CharStream that reads from file
        CharStream input = CharStreams.fromFileName("input/entrada.txt");
        // System.out.println(input.getSourceName());
        
        // create a lexer that feeds off of input CharStream
        compiladoresLexer lexer = new compiladoresLexer(input);
        
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // create a parser that feeds off the tokens buffer
        compiladoresParser parser = new compiladoresParser(tokens);
                
        ErrorsListener errorsListener = new ErrorsListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorsListener);

        compiladoresBaseListener escucha = new MiListener();

        parser.addParseListener(escucha);

        parser.programa();        
    }
}
