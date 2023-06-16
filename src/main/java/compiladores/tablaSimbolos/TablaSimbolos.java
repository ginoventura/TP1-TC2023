package compiladores.tablaSimbolos;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Map.Entry;
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

    public void agregarEnContextoActual(String clave, Id valor){
        this.tablaSimbolos.getLast().put(clave, valor);
    }

    public void quitarContexto() {
        this.tablaSimbolos.removeLast();
    }

    public LinkedList<HashMap<String, Id>> getTablaSimbolos() {
        return tablaSimbolos;
    }

        public ArrayList<String> idSinUsar() {
        ArrayList<String> variablesSinUsar = new ArrayList<String>();
        for (Entry<String, Id> entrada : tablaSimbolos.getLast().entrySet()) {
            if(!entrada.getValue().isUsado()){
                if (entrada.getValue() instanceof Funcion && !entrada.getValue().isInicializado()) {
                    continue;
                }else{
                    variablesSinUsar.add(entrada.getKey()); 
                }
            }
        }
        return variablesSinUsar;
    }

    public Id buscarIdGlobal(String id){
        Id simbolo = buscarIdLocal(id);
        if(simbolo != null){
            return simbolo;
        }else{
            ListIterator<HashMap<String, Id>> iteratorSimbolos = this.tablaSimbolos.listIterator(tablaSimbolos.size() - 1);
            while(iteratorSimbolos.hasPrevious()){
                simbolo = iteratorSimbolos.previous().get(id);
                if( simbolo != null ){
                    return simbolo;
                }
            }
        }
        return simbolo;
    }

    public Id buscarIdLocal(String id){
        Id simbolo = tablaSimbolos.getLast().get(id);
        if(simbolo != null){
            return simbolo;
        }
        return null;
    }

    @Override
    public String toString() {
        return tablaSimbolos.toString();
    }
}