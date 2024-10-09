package FaseSintactica;

import java.util.List;
import java.util.ArrayList;

import FaseLexica.TablaSimbolos;
import FaseLexica.Token;

public class FaseSintactica {

    private List<Token> tokens;
    private int posicion;
    private TablaSimbolos tablaSimbolos;
    private List<String> ListaDeIdentificadores;
    private int error;
    private String archivo;

    public FaseSintactica(List<Token> tokens, TablaSimbolos tablaSimbolos, String archivo) {
        this.tokens = tokens;
        this.posicion = 0;
        this.tablaSimbolos = tablaSimbolos;
        this.ListaDeIdentificadores = new ArrayList<>();
        this.archivo = archivo;
        this.error = 0;
    }

    // Se inicia el análisis del programa, si se encuentra algun error de token no esperado se imprime el error y se termina el programa
    public void analizarPrograma() {
        programa();
        tablaSimbolos.guardarEnArchivo(archivo);
        System.exit(1);
    }

    // Se verifica la producción 'programa'
    private boolean programa() {
        if (expresion()) {
            if (consumirToken("PUNTO_COMA")) {
                ListaDeIdentificadores.clear();
                while (expresion()) {
                    if (!consumirToken("PUNTO_COMA")) {
                        // Se elimina el identificador de la Tabla de Símbolos ya que tenía un error
                        tablaSimbolos.eliminar(ListaDeIdentificadores.get(0));

                        if (consumirToken("ASIGNACION") && error == 0) {
                            errorSintactico("La línea " + tokens.get(posicion-1).getLinea() + " contiene un error en su gramática, falta token NUMERO o IDENTIFICADOR");
                        } else if (error == 0) {
                            errorSintactico("La línea " + tokens.get(posicion-1).getLinea() + " contiene un error en su gramática, falta token ;");
                        }
                        error = 0;
                        return false;
                    }
                    ListaDeIdentificadores.clear();
                }
                return true;
            }
            errorSintactico("La línea " + tokens.get(posicion-1).getLinea() + " contiene un error en su gramática, falta token ;");
            return false;
        }
        errorSintactico("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token IDENTIFICADOR");
        return false;
    }

    // Se verifica la producción 'expresion'
    private boolean expresion() {
        int backup = posicion;
        if (identificador() && consumirToken("ASIGNACION") && expresion()) {
            return true;
        }
        // Volvemos a la posición guardada si el if no se cumple
        posicion = backup;
        if (termino()) {
            while (consumirToken("SUMA") || consumirToken("RESTA")) {
                if (!termino()) {
                    errorSintactico("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token NUMERO o IDENTIFICADOR");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Se verifica la producción 'termino'
    private boolean termino() {
        if (factor()) {
            while (consumirToken("MULTIPLICACION") || consumirToken("DIVISION")) {
                if (!factor()) {
                    errorSintactico("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token NUMERO o IDENTIFICADOR");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Se verifica la producción 'factor'
    private boolean factor() {
        int backup = posicion;
        if (identificador() || numero()) {
            return true;
        }
        posicion = backup;
        if (consumirToken("PARENTESIS_IZQ")) {
            if (expresion() && consumirToken("PARENTESIS_DER")) {
                return true;
            }
            errorSintactico("La línea " + tokens.get(posicion).getLinea() + " contiene un error en su gramática, falta token )");
            return false;
        }
        return false;
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
    private void errorSintactico(String mensaje) {
        System.out.println("Error [Fase Sintáctica]: " + mensaje);
        error++;
    }
}
