package compiladores.tablaSimbolos;

import java.util.LinkedList;
import java.util.HashMap;

public class TablaSimbolos {
    
    private LinkedList<HashMap<String, Id>> tablaSimbolos;

    public TablaSimbolos() {
        tablaSimbolos = new LinkedList<HashMap<String, Id>>();
    }

    public LinkedList<HashMap<String, Id>> getTablaSimbolos() {
        return tablaSimbolos;
    }

    @Override
    public String toString() {
        return tablaSimbolos.toString();
    }
}