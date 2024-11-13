package FaseSintactica.AST;

import java.util.ArrayList;
import java.util.List;

import FaseSintactica.Visitante.IVisitanteAST;

// Clase que contiene todas las expresiones del programa
public class NodoPrograma extends NodoAST {
    private List<NodoAST> expresiones;

    public NodoPrograma() {
        this.expresiones = new ArrayList<>();
    }

    // Agrega una expresión al nodo programa
    public void agregarExpresion(NodoAST expresion) {
        expresiones.add(expresion);
    }

    public List<NodoAST> getExpresiones() {
        return expresiones;
    }

    @Override
    public void aceptar(IVisitanteAST visitor) {
        for (NodoAST expresion : expresiones) {
            expresion.aceptar(visitor);
        }
    }

    @Override
    public String obtenerValor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerValor'");
    }
}