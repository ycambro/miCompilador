import FaseLexica.FaseLexica;
import FaseLexica.TablaSimbolos;
import FaseSemantica.FaseSemantica;
import FaseSintactica.FaseSintactica;
import FaseSintactica.AST.NodoAST;
import FaseSintactica.Visitante.IVisitanteAST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import FaseGeneracionCodigo.GeneradorCodigo;
import FaseGeneracionCodigo.GuardaCodigoSh;

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
        IVisitanteAST faseSemantica = new FaseSemantica(faseSintactica.obtenerTablaSimbolos());
        ast.aceptar(faseSemantica);
        System.out.println("Confirmación [Fase Semántica]: Análisis semántico completado con éxito.");

        // Se inicia la fase de generación de código
        IVisitanteAST generadorCodigo = new GeneradorCodigo();
        ast.aceptar(generadorCodigo);
        System.out.println("Confirmación [Fase Generación de Código]: Generación de código completada con éxito.");

        // Guarda el código generado en un archivo
        new GuardaCodigoSh(((GeneradorCodigo) generadorCodigo).obtenerCodigo(), ((GeneradorCodigo) generadorCodigo).obtenerTablaSimbolos());

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
