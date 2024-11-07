package FaseSintactica;

import java.util.List;
import java.util.ArrayList;

import FaseLexica.TablaSimbolos;
import FaseLexica.Token;
import FaseSintactica.AST.NodoAST;
import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;
import FaseSintactica.AST.NodoPrograma;

public class FaseSintactica {

    private List<Token> tokens;
    private int posicion;
    private TablaSimbolos tablaSimbolos;
    private List<String> ListaDeIdentificadores;

    public FaseSintactica(List<Token> tokens, TablaSimbolos tablaSimbolos) {
        this.tokens = tokens;
        this.posicion = 0;
        this.tablaSimbolos = tablaSimbolos;
        this.ListaDeIdentificadores = new ArrayList<>();
    }

    // Se inicia el análisis del programa, se llama a la producción 'programa' y se guarda la Tabla de Símbolos en un archivo si todo sale bien.
    public NodoAST analizarPrograma() {
        NodoAST ast = programa();  // Devuelve el AST en lugar de booleano
        System.out.println("Confirmación [Fase Sintáctica]: Análisis sintáctico completado con éxito.");
        return ast;
    }

    public TablaSimbolos obtenerTablaSimbolos() {
        return tablaSimbolos;
    }

    // Se verifica la producción 'programa' si hay un error de falta de identificador se reporta y se termina el programa, al igual si falta un punto y coma.
    private NodoAST programa() {
        NodoPrograma nodoPrograma = new NodoPrograma(); // Crear un nodo programa
        NodoAST nodoExpresion = expresion(); // Verificar la producción 'expresion'

        if (nodoExpresion != null && consumirToken("PUNTO_COMA")) {
            nodoPrograma.agregarExpresion(nodoExpresion);  // Agrega la expresión al nodo programa
            ListaDeIdentificadores.clear();
            
            while (posicion <= tokens.size() && (nodoExpresion = expresion()) != null) {
                if (!consumirToken("PUNTO_COMA")) {
                    reportarError("Falta token ;");
                    return null;
                }
                nodoPrograma.agregarExpresion(nodoExpresion);  // Agrega la expresion al nodo programa
                ListaDeIdentificadores.clear();
            }
            return nodoPrograma;  // Retorna el nodo del programa con todas las expresiones
        }
        reportarError("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token IDENTIFICADOR o NUMERO");
        return null;
    }

    // Se verifica la producción 'expresion'
    private NodoAST expresion() {
        int backup = posicion;
        if (identificador() && consumirToken("ASIGNACION")) {
            tablaSimbolos.declarar(ListaDeIdentificadores.get(0)); // Se declara el identificador en la Tabla de Símbolos
            tablaSimbolos.cambiarLinea(ListaDeIdentificadores.get(0), tokens.get(posicion - 1).getLinea()); // Se cambia la línea del identificador en la Tabla de Símbolos
            NodoAST ladoDerecho = expresion();
            NodoIdentificador nodoIdentificador = new NodoIdentificador(ListaDeIdentificadores.get(0));
            return new NodoOperacion("=", nodoIdentificador, ladoDerecho);
        }
        // Volvemos a la posición guardada si el if no se cumple
        posicion = backup;
        NodoAST nodoTermino = termino();
        if (nodoTermino != null) {
            while (consumirToken("SUMA") || consumirToken("RESTA")) {
                String operador = tokens.get(posicion - 1).getValor(); // SUMA o RESTA
                NodoAST nodoDerecho = termino();
                if (nodoDerecho == null) {
                    reportarError("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token NUMERO o IDENTIFICADOR");
                    return null;
                }
                nodoTermino = new NodoOperacion(operador, nodoTermino, nodoDerecho);
            }
            return nodoTermino;
        }
        return null;
    }


    // Se verifica la producción 'termino'
    private NodoAST termino() {
        NodoAST nodoFactor = factor();
        if (nodoFactor != null) {
            while (consumirToken("MULTIPLICACION") || consumirToken("DIVISION")) {
                String operador = tokens.get(posicion - 1).getValor(); // MULTIPLICACION o DIVISION
                NodoAST nodoDerecho = factor();
                if (nodoDerecho == null) {
                    reportarError("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token NUMERO o IDENTIFICADOR");
                    return null;
                }
                nodoFactor = new NodoOperacion(operador, nodoFactor, nodoDerecho);
            }
            return nodoFactor;
        }
        return null;
    }

    // Se verifica la producción 'factor'
    private NodoAST factor() {
    int backup = posicion;
    if (identificador()) {
        return new NodoIdentificador(ListaDeIdentificadores.get(ListaDeIdentificadores.size() - 1)); //Se agrega el último identificador de la lista ya que es el más reciente
    }
    posicion = backup;
    if (numero()) {
        return new NodoNumero(Integer.parseInt(tokens.get(posicion - 1).getValor()));
    }
    posicion = backup;
    if (consumirToken("PARENTESIS_IZQ")) {
        NodoAST nodoExpresion = expresion();
        if (nodoExpresion != null && consumirToken("PARENTESIS_DER")) {
            return nodoExpresion;
        }
        reportarError("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token )");
        return null;
    }
    return null;
}


    // Se verifica la producción 'numero'
    private boolean numero() {
        return consumirToken("NUMERO");
    }

    // Se verifica la producción 'identificador'
    private boolean identificador() {
        return consumirToken("IDENTIFICADOR");
    }

    // Se consume un token y se avanza en la lista de tokens
    private boolean consumirToken(String tipoEsperado) {
        if (posicion < tokens.size() && tokens.get(posicion).getAtributo().equals(tipoEsperado)) {
            if (tipoEsperado.equals("IDENTIFICADOR")) {
                ListaDeIdentificadores.add(tokens.get(posicion).getValor()); // Se agrega el identificador a la lista por si se debe eliminar de la Tabla de Símbolos
            }
            posicion ++;
            return true;
        }
        return false;
    }

    // Se imprime un mensaje de error de token "esperado" si se encontró alguno y se termina el programa
    private void reportarError(String mensaje) {
        System.out.println("Error [Fase Sintáctica]: " + mensaje);

        // Se elimina el identificador de la Tabla de Símbolos ya que tenía un error
        if (ListaDeIdentificadores.size() > 0) {
            tablaSimbolos.eliminar(ListaDeIdentificadores.get(0));
        }
        System.exit(1);
    }
}
