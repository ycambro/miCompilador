import FaseLexica.FaseLexica;
import FaseSemantica.FaseSemantica;
import FaseSintactica.FaseSintactica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        // Se inicia la fase sintáctica
        FaseSintactica faseSintactica = new FaseSintactica(faseLexica.obtenerTokens(), faseLexica.obtenerTablaSimbolos());

        // Se inicia la fase semántica
        FaseSemantica faseSemantica = new FaseSemantica(faseSintactica.obtenerTablaSimbolos());
        faseSemantica.analizar(faseSintactica.analizarPrograma());

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
