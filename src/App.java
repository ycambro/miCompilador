import FaseLexica.FaseLexica;
import FaseSintactica.FaseSintactica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        //Espera el argumento de la ruta del archivo
        if (args.length > 1) {
            System.out.println("Uso: [NOMBRE DEL PROGRAMA] [ARCHIVO DE ENTRADA] [ARCHIVO SALIDA]");
            return;
        }

        //String input = leerArchivo(args[0]);
        String input = leerArchivo("ej.txt");
        if (input == null) {
            return;
        }

        // Se inicia la fase léxica
        FaseLexica faseLexica = new FaseLexica(input);
        faseLexica.obtenerTablaSimbolos().guardarEnArchivo("tabla.txt");
        //faseLexica.obtenerTablaSimbolos().guardarEnArchivo(args[1]);

        // Se inicia la fase sintáctica
        FaseSintactica faseSintactica = new FaseSintactica(faseLexica.obtenerTokens(), faseLexica.obtenerTablaSimbolos(), "tabla.txt");
        faseSintactica.analizarPrograma();

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
