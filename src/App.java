import FaseLexica.FaseLexica;
import FaseLexica.TablaSimbolos;
import FaseSemantica.FaseSemantica;
import FaseSintactica.FaseSintactica;
import FaseSintactica.AST.NodoAST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import FaseGeneracionCodigo.GeneradorCodigo;

public class App {
    public static void main(String[] args) {
        //Espera el argumento de la ruta del archivo
        if (args.length != 1) {
            System.out.println("Uso: [NOMBRE DEL PROGRAMA] [ARCHIVO DE ENTRADA]");
            return;
        }

        String input = leerArchivo(args[0]);
        if (input == null || input.isEmpty()) {
            return;
        }

        // Se inicia la fase léxica
        FaseLexica faseLexica = new FaseLexica(input);
        TablaSimbolos tablaSimbolos = faseLexica.obtenerTablaSimbolos();

        // Se inicia la fase sintáctica
        FaseSintactica faseSintactica = new FaseSintactica(faseLexica.obtenerTokens(), tablaSimbolos);
        NodoAST ast = faseSintactica.analizarPrograma();

        // Se inicia la fase semántica
        FaseSemantica faseSemantica = new FaseSemantica(faseSintactica.obtenerTablaSimbolos());
        faseSemantica.analizar(ast);

        // Se inicia la fase de generación de código
        GeneradorCodigo generadorCodigo = new GeneradorCodigo();
        generadorCodigo.generarCodigo(ast);

    }

    // Lee el archivo de entrada
    private static String leerArchivo(String rutaArchivo) {
        try {
            return new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
    }
}
