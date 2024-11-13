package FaseSintactica.AST;

import FaseSintactica.Visitante.IVisitanteAST;

// Clase que representa un operador en el árbol de sintaxis abstracta
public class NodoOperacion extends NodoAST {
    private String operador;
    private NodoAST izquierdo;
    private NodoAST derecho;

    public NodoOperacion(String operador, NodoAST izquierdo, NodoAST derecho) {
        this.operador = operador;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public NodoAST obtenerIzquierdo() {
        return izquierdo;
    }

    public NodoAST obtenerDerecho() {
        return derecho;
    }

    @Override
    public void aceptar(IVisitanteAST visitor) {
        visitor.visitar(this);
    }

    @Override
    public String obtenerValor() {
        return operador;
    }
}