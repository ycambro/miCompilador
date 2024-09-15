package FaseLexica;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FaseLexica {
    public static void main(String[] args) {
        //Espera el argumento de la ruta del archivo
        if (args.length < 1) {
            System.out.println("Uso: [NOMBRE DEL PROGRAMA] [ARCHIVO DE ENTRADA]");
            return;
        }


        String archivoEntrada = args[0];

        String input = leerArchivo(archivoEntrada);
        if (input == null) {
            return;
        }

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.obtenerTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }

        System.out.println("\nImprimir tabla de simbolos? (s/n)");
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.nextLine().equals("s")) {
                lexer.imprimirTablaSimbolos();
            }
        }

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
