package FaseSintactica.AST;

import FaseSintactica.Visitante.IVisitanteAST;

// Clase que representa un número en el árbol de sintaxis abstracta
public class NodoNumero extends NodoAST {
    private int valor;

    public NodoNumero(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public void aceptar(IVisitanteAST visitor) {
        visitor.visitar(this);
    }
}
