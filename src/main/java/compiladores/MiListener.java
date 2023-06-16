package compiladores;

import compiladores.compiladoresParser.*;
import compiladores.tablaSimbolos.Funcion;
import compiladores.tablaSimbolos.Id;
import compiladores.tablaSimbolos.TablaSimbolos;
import compiladores.tablaSimbolos.Variable;
import compiladores.errores.CustomErrors;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MiListener extends compiladoresBaseListener {

    private TablaSimbolos simbolos = new TablaSimbolos();
    private CustomErrors errores = new CustomErrors();

    private String posicionDelToken(Token token){
        return "[" + token.getLine() + ":" + token.getCharPositionInLine() + "]";
    }

    private Id getID(String id) {return simbolos.buscarIdGlobal(id); }

    private Id getIDLocal(String id) {return simbolos.buscarIdLocal(id); }

    private boolean esFuncion(Id id){ return id instanceof Funcion; }

    private String getTipoString(int type) {
        switch (type) {
            case compiladoresLexer.ENTERO:
                return "int";
            case compiladoresLexer.DECIMAL:
                return "double";
            default:
                return null;
        }
    }

    private boolean coincideTipoDato(String tipoDato, String tipoDatoFactor) {
        if (tipoDato.equals(tipoDatoFactor))
            return true;
        else
            return false;
    }

    private boolean analisisFactores(Id caller, String tipoDato, Collection<ParseTree> factores) {
        FactorContext fc;
        boolean error = true;
        for (ParseTree parseTree : factores) {
            fc = (FactorContext) parseTree;
            
            if (fc.ID() != null){
                String idFactor = fc.ID().getText();
                Id id = getID(idFactor);
                if (id == null){
                    errores.idNoDeclarado(posicionDelToken(fc.getStart()), idFactor);
                    error = false;
                }else if(!id.isInicializado()){
                    errores.idNoInicializado(posicionDelToken(fc.getStart()), idFactor);
                    error = false;
                }else{
                    id.setUsado(true);
                    if (tipoDato != null  && !coincideTipoDato(tipoDato, id.getTipoDato())){
                        if(esFuncion(caller))
                            errores.idDiferenteTiposParamArgs(posicionDelToken(fc.getStart()), id.getTipoDato(), tipoDato, id.getTokenNombre());
                        else
                            errores.idDistintosTipos(posicionDelToken(fc.getStart()), id.getTipoDato(), tipoDato);
                    }
                }
            } else if (fc.ENTERO() != null || fc.DECIMAL() != null) {
                if (tipoDato != null  && !coincideTipoDato(tipoDato, getTipoString(fc.getStop().getType()))){
                    if(esFuncion(caller))
                        errores.idDiferenteTiposParamArgs(posicionDelToken(fc.getStart()), getTipoString(fc.getStop().getType()), tipoDato, fc.getText());
                    else
                        errores.idDistintosTipos(posicionDelToken(fc.getStart()), getTipoString(fc.getStop().getType()), tipoDato);
                }
            }
        }
        return error;
    }

    private void procesarInstruccionAsignacion(AsignacionContext ctx) {
        Id id = getID(ctx.ID().getText());
        if (id != null) {
            Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ctx);
            if(analisisFactores(null, id.getTipoDato(), factores))
                id.setInicializado(true);
        } else
            errores.idNoDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
    }

    private void procesarDeclaracion(DeclaracionContext ctx) {
        Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ctx);
        if (getIDLocal(ctx.ID().getText()) == null) {
            Id id = new Variable(false, false, ctx.tipoDato().getText(), ctx.ID().getText());
            simbolos.agregarEnContextoActual(ctx.ID().getText(), id);
            if (ctx.inicializacion() != null) {
                if(analisisFactores(null, ctx.tipoDato().getText(), factores))
                    id.setInicializado(true);
            }
        } else{
            analisisFactores(null, ctx.tipoDato().getText(), factores);
            errores.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }
    }

    private Id procesarFuncion(DeclaracionFuncionContext ctx){
        Id id = getIDLocal(ctx.ID().getText());
        if(id == null){
            List<Variable> parametros = getParametros(ctx.parametrosDecl());
            if(parametros != null){
                Id idFunction = new Funcion(true, false, ctx.tipoDato().getText(), ctx.ID().getText(), parametros);
                simbolos.agregarEnContextoActual(ctx.ID().getText(), idFunction);
                return idFunction;
            }
        }else if(!esFuncion(id)){
            errores.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }else if(esFuncion(id) && id.isInicializado()){
            errores.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }else if(esFuncion(id) && !id.isInicializado()){
            if(!((Funcion)id).parametrosSonIguales(getParametros(ctx.parametrosDecl())))
                errores.ordenPrototipo(posicionDelToken(ctx.getStart()));
            else
                id.setInicializado(true);
                return id;
        }
        return null;
    }

    private void procesarLlamadaFuncion(String tipoDato, LlamadaFuncionContext ctx){
        Id id = getID(ctx.ID().getText());
        Collection<ParseTree> operations = Trees.findAllRuleNodes(ctx, compiladoresParser.RULE_llamadaFuncion);
        Object[] oper = operations.toArray();
        Collection<ParseTree> factorCountFirstOperation = Trees.findAllRuleNodes((ParseTree)oper[0], compiladoresParser.RULE_listaParamLlam);

        if(id == null)
            errores.idNoDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        else if(esFuncion(id) && id.isInicializado()){
            List<Variable> parametros = ((Funcion)id).getParametros();
            Collection<ParseTree> factores;

            if(factorCountFirstOperation.size() > 0 && ((Funcion)id).getParametros().size() != operations.size()){
                errores.cantidadParamArgsDistinta(posicionDelToken(ctx.getStart()));
            }
            else if(tipoDato != null && !coincideTipoDato(tipoDato, id.getTipoDato()))
                errores.idDistintosTipos(posicionDelToken(ctx.getStart()), id.getTipoDato(), tipoDato);
            
            id.setUsado(true);
            for(int i=0; i < parametros.size();  i++){
                if(i > (operations.size() - 1)) { break; }
                factores = Trees.findAllRuleNodes((ParseTree)oper[i], compiladoresParser.RULE_listaParamLlam);
                analisisFactores(id, parametros.get(i).getTipoDato(), factores);
            }
        }else if(esFuncion(id) && !id.isInicializado()){
            errores.funcionNoDeclarada(posicionDelToken(ctx.getStart()), id.getTokenNombre());
        }else if(!esFuncion(id))
            errores.declaradoComoVariable(posicionDelToken(ctx.getStart()), id.getTokenNombre());
    }

    private boolean tieneParametros(List<Variable> parametros, String nameVar){
        return parametros.stream()
                         .filter(param -> param.getTokenNombre().equals(nameVar))
                         .findFirst()
                         .isPresent();
    }

    private List<Variable> getParametros(ParametrosDeclContext ctx){
        List<Variable> params = new ArrayList<Variable>();
        Collection<ParseTree> parametros = Trees.findAllRuleNodes(ctx, compiladoresParser.RULE_declaracionFuncion);
        for (ParseTree parseTree : parametros) {
            DeclaracionContext decl = (DeclaracionContext)parseTree;
            if(tieneParametros(params, decl.ID().getText())){
                errores.idRedefinido(posicionDelToken(ctx.getStart()), decl.ID().getText());
                return null;
            }
            Variable var = new Variable(true, false, decl.tipoDato().getText(), decl.ID().getText());
            params.add(var);
        }
        return params;
    }

    private Collection<ParseTree> encontrarFactoresSinArgumentos(ParseTree ctx){
        Collection<ParseTree> factores = Trees.findAllRuleNodes(ctx, compiladoresParser.RULE_factor);
        Collection<ParseTree> llamadaFuncion = Trees.findAllRuleNodes(ctx, compiladoresParser.RULE_llamadaFuncion);
        Collection<ParseTree> factoresLlamadaFuncion;
        for (ParseTree parseTree : llamadaFuncion) {
            factoresLlamadaFuncion = Trees.findAllRuleNodes(parseTree, compiladoresParser.RULE_factor);
            factores.removeAll(factoresLlamadaFuncion);
        }
        return factores;
    }

    @Override
    public void enterPrograma(ProgramaContext ctx) {
        System.out.println("Comienza el programa");
        simbolos.agregarContexto();
    }

    @Override
    public void exitPrograma(ProgramaContext ctx) {
        errores.idNoUsado(simbolos.idSinUsar());
        simbolos.quitarContexto();

        System.out.println("Finaliza programa");
    }

    @Override
    public void exitAsignacion(AsignacionContext ctx) {
        procesarInstruccionAsignacion(ctx);
    }

    @Override
    public void exitDeclaracionFuncion(DeclaracionFuncionContext ctx) {
        if(simbolos.getTablaSimbolos().size() == 1){
            Id id = getID(ctx.ID().getText()); 
            if(id == null){
                List<Variable> parametros = getParametros(ctx.parametrosDecl());
                if(parametros != null){
                    Id prototype = new Funcion(false, false, ctx.tipoDato().getText(), ctx.ID().getText(), parametros);
                    simbolos.agregarEnContextoActual(ctx.ID().getText(), prototype);
                }
            }else{
                errores.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
            }
        }else{
            errores.prototipoContexto(posicionDelToken(ctx.getStart()));
        }
    }

    @Override
    public void enterBloque(BloqueContext ctx) {
        if (ctx.getParent() instanceof DeclaracionFuncionContext) {
            DeclaracionFuncionContext funcionContext = (DeclaracionFuncionContext)ctx.getParent();
            Id id = procesarFuncion(funcionContext);
            simbolos.agregarContexto();
            if (id != null) {
                ((Funcion)id).getParametros().stream().forEach(param -> simbolos.agregarEnContextoActual(param.getTokenNombre(), param));
            } else {
                getParametros(funcionContext.parametrosDecl()).stream().forEach(param -> simbolos.agregarEnContextoActual(param.getTokenNombre(), param));
            }
        } else {
            simbolos.agregarContexto();
        }
    }
    
    @Override
    public void exitBloque(BloqueContext ctx) {
        errores.idNoUsado(simbolos.idSinUsar());
        simbolos.quitarContexto();
    }

    @Override
    public void exitLlamadaFuncion(LlamadaFuncionContext ctx) {
        if(ctx.getParent() instanceof InstruccionContext){
            Id id = getID(ctx.ID().getText());
            if(id != null){
                getID(ctx.ID().getText()).setUsado(true);
                procesarLlamadaFuncion(null, ctx);
            }
        }
    }

    @Override
    public void exitInstruccion(InstruccionContext ctx) {
        if(ctx.declaracion() != null)
            procesarDeclaracion(ctx.declaracion());
        //System.out.println(simbolos);
    }

}