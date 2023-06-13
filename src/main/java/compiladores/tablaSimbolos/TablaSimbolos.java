package compiladores.tablaSimbolos;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;

public class TablaSimbolos {
    
    private LinkedList<HashMap<String, Id>> tablaSimbolos;

    public TablaSimbolos() {
        tablaSimbolos = new LinkedList<HashMap<String, Id>>();
    }

    public void agregarContexto() {
        HashMap<String, Id> contexto = new HashMap<String, Id>();
        this.tablaSimbolos.add(contexto);
    }

    public void quitarContexto(){
        this.tablaSimbolos.removeLast();
    }

    public ArrayList<String> idSinUsar() {
        ArrayList<String> variablesSinUsar = new ArrayList<String>();

        for (Entry<String, Id> entrada : tablaSimbolos.getLast().entrySet()) {
            if (!entrada.getValue().isUsado() && !entrada.getValue().getTokenNombre().equals("main")) {
                if (entrada.getValue() instanceof Funcion && !entrada.getValue().isInicializado()) {
                    continue;
                } else {
                    variablesSinUsar.add(entrada.getKey());
                }
            }
        }
        return variablesSinUsar;
    }

    public Id buscarIdGlobal(String id) {
        Id simbolo = buscarIdLocal(id);

        if(simbolo != null) {
            return simbolo;
        } else {
            ListIterator<HashMap<String, Id>> iteradorSimbolos = this.tablaSimbolos.listIterator(tablaSimbolos.size() - 1);
            
            while (iteradorSimbolos.hasPrevious()) {
                simbolo = iteradorSimbolos.previous().get(id);
                if (simbolo != null) {
                    return simbolo;
                }
            }
        }
        return simbolo;
    }

    public Id buscarIdLocal(String id) {
        Id simbolo = tablaSimbolos.getLast().get(id);
        if(simbolo != null) {
            return simbolo;
        }
        return null;
    }

    public LinkedList<HashMap<String, Id>> getTablaSimbolos() {
        return tablaSimbolos;
    }

    @Override
    public String toString() {
        return tablaSimbolos.toString();
    }
}