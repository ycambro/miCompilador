package FaseSemantica;

import FaseLexica.TablaSimbolos;
import FaseSintactica.AST.NodoAST;
import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;
import FaseSintactica.Visitante.IVisitanteAST;

public class FaseSemantica implements IVisitanteAST {
    private TablaSimbolos tablaSimbolos;
    private int linea;

    public FaseSemantica(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
        this.linea = 0;
    }

    @Override
    public void visitar(NodoOperacion nodo) {
        NodoAST izquierdo = nodo.obtenerIzquierdo();
        NodoAST derecho = nodo.obtenerDerecho();

        // Verificar si es una operación de división y si el hijo derecho es 0
        if (nodo.obtenerValor().equals("/")) {
            if (derecho instanceof NodoNumero) {
                NodoNumero nodoDerecho = (NodoNumero) derecho;
                if (nodoDerecho.obtenerValor() == "0") {
                    reportarError("La línea " + linea + " contiene un error, operación inválida división por 0.");
                }
            }
        }

        // Se recorren los hijos del nodo
        izquierdo.aceptar(this);
        derecho.aceptar(this);
    }

    @Override
    public void visitar(NodoNumero nodo) {
        // Los números siempre son válidos asi que no se hace nada
    }

    // Verifica si el identificador está declarado en la tabla de símbolos
    @Override
    public void visitar(NodoIdentificador nodo) {
        linea = tablaSimbolos.obtenerInformacionSimbolo(nodo.obtenerValor()).getLinea();
        if (!tablaSimbolos.existe(nodo.obtenerValor()) || !tablaSimbolos.obtenerInformacionSimbolo(nodo.obtenerValor()).getEstaDeclarado()) {
            reportarError("La línea " + linea + " contiene un error, no declarado identificador " + nodo.obtenerValor() +".");
        }
    }

    private void reportarError(String mensaje) {
        System.out.println("Error [Fase Semántica]: " + mensaje);
        System.exit(1);
    }
}
