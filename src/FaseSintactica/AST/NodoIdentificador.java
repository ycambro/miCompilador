package FaseSintactica.AST;

import FaseSintactica.Visitante.IVisitanteAST;

// Clase que representa un nodo identificador en el árbol de sintaxis abstracta
public class NodoIdentificador extends NodoAST {
    private String nombre;

    public NodoIdentificador(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void aceptar(IVisitanteAST visitor) {
        visitor.visitar(this);
    }

    @Override
    public String obtenerValor() {
        return nombre;
    }
}
