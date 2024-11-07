package FaseSintactica.Visitante;

import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;

// Interfaz que define el comportamiento de un visitante del árbol de sintaxis abstracta
public interface IVisitanteAST {
    void visitar(NodoOperacion nodo);
    void visitar(NodoNumero nodo);
    void visitar(NodoIdentificador nodo);
}
