package FaseSintactica.AST;

import FaseSintactica.Visitante.IVisitanteAST;

// Clase abstracta que representa un nodo del árbol de sintaxis abstracta
public abstract class NodoAST {
    public abstract void aceptar(IVisitanteAST visitor);
    public abstract String obtenerValor();
}
