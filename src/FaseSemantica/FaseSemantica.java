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
    private String identificadorBackup;

    public FaseSemantica(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
        this.linea = 0;
    }

    // Inicia el análisis semántico
    public void analizar(NodoAST ast) {
        ast.aceptar(this);
        System.out.println("Confirmación [Fase Semántica]: Análisis semántico completado con éxito.");
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

        // Se recorre el hijo izquierdo
        izquierdo.aceptar(this);

        // Verificar si es una asignación a sí mismo
        if (nodo.obtenerValor().equals("=")) {
            if (izquierdo instanceof NodoIdentificador) {
                identificadorBackup = izquierdo.obtenerValor();
            }
        }

        // Se recorre el hijo derecho
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
        if (nodo.obtenerValor().equals(identificadorBackup)) {
            reportarError("La línea " + linea + " contiene un error, asignación a sí mismo del identificador " + nodo.obtenerValor() + ".");
        }
    }

    private void reportarError(String mensaje) {
        System.out.println("Error [Fase Semántica]: " + mensaje);
        System.exit(1);
    }
}
