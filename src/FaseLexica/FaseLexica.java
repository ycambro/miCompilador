package FaseLexica;
import java.util.List;
import java.util.Scanner;

public class FaseLexica {
    private List<Token> tokens;
    private Lexer lexer;

    public FaseLexica(String input) {
        this.lexer = new Lexer(input);
        this.tokens = lexer.obtenerTokens();
    }

    public List<Token> obtenerTokens() {
        return tokens;
    }

    public void imprimirTokens() {
        for (Token token : tokens) {
            System.out.println(token);
        }
    }	

    public TablaSimbolos obtenerTablaSimbolos() {
        return lexer.getTablaSimbolos();
    }

    public void imprimirTablaSimbolos () {
        System.out.println("\nImprimir tabla de simbolos? (s/n)");
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.nextLine().equals("s")) {
                lexer.imprimirTablaSimbolos();
            }
        }
    }
}
