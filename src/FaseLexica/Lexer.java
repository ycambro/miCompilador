package FaseLexica;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String entrada;
    private int pos;
    private int length;
    private char caracterAct;
    private int linea;
    private TablaSimbolos tablaSimbolos;

    public Lexer(String entrada) {
        this.entrada = entrada;
        this.pos = 0;
        this.length = entrada.length();
        this.caracterAct = entrada.charAt(0);
        this.linea = 1;
        this.tablaSimbolos = new TablaSimbolos();
    }

    //Avanza el caracter actual
    private void avanzar() {
        pos ++;
        if (pos >= length) {
            caracterAct = '\0'; // Fin del archivo
        } else {
            caracterAct = entrada.charAt(pos);
            if (caracterAct == '\n') {
                linea ++;
            }
        }
    }

    private void saltarEspacios() {
        while (caracterAct != '\0' && Character.isWhitespace(caracterAct)) {
            avanzar();
        }
    }

    //Revisa el identificador y verifica que sea válido
    private Token leerIdentificador() {
        StringBuilder identificador = new StringBuilder();
        while (caracterAct != '\0' && Character.isLetter(caracterAct)) {
            identificador.append(caracterAct);
            avanzar();
        }

        if (caracterAct != '\0' && Character.isDigit(caracterAct)) {
            while (caracterAct != '\0' && !Character.isWhitespace(caracterAct)) {
                identificador.append(caracterAct);
                avanzar();
            }
            reportarError(identificador.toString());
        }
        String identificadorStr = identificador.toString();

        if (!tablaSimbolos.existe(identificadorStr)) {
            tablaSimbolos.agregar(identificadorStr, new InformacionSimbolo(linea));
        }

        return new Token(identificadorStr, "IDENTIFICADOR");
    }

    //Revisa el número y verifica que sea válido
    private Token leerNumero() {
        StringBuilder numero = new StringBuilder();
        while (caracterAct != '\0' && Character.isDigit(caracterAct)) {
            numero.append(caracterAct);
            avanzar();
        }
        if (caracterAct != '\0' && Character.isLetter(caracterAct)) {
            while (caracterAct != '\0' && !Character.isWhitespace(caracterAct)) {
                numero.append(caracterAct);
                avanzar();
            }
            reportarError(numero.toString());
        }

        return new Token(numero.toString(), "NUMERO");
    }

    private void reportarError(String lexema) {
        System.out.println("Error [Fase Léxica]: La línea " + linea + " contiene un error, lexema no reconocido: " + lexema);   
        System.exit(1);     
    }

    //Esta función lee los tokens y revisa su validación, además de agregarlos a una lista para imprimir al final
    public List<Token> obtenerTokens() {
        List<Token> tokens = new ArrayList<>();

        while (caracterAct != '\0') {
            saltarEspacios();

            if (Character.isLetter(caracterAct) && Character.isLowerCase(caracterAct)) {
                tokens.add(leerIdentificador());
            } else if (Character.isDigit(caracterAct)) {
                tokens.add(leerNumero());
            } else if (caracterAct == '+') {
                tokens.add(new Token("+", "SUMA"));
                avanzar();
            } else if (caracterAct == '-') {
                tokens.add(new Token("-", "RESTA"));
                avanzar();
            } else if (caracterAct == '*') {
                tokens.add(new Token("*", "MULTIPLICACION"));
                avanzar();
            } else if (caracterAct == '/') {
                tokens.add(new Token("/", "DIVISION"));
                avanzar();
            } else if (caracterAct == '=') {
                tokens.add(new Token("=", "ASIGNACION"));
                avanzar();
            } else if (caracterAct == ';') {
                tokens.add(new Token(";", "PUNTO_COMA"));
                avanzar();
            } else if (caracterAct == '(') {
                tokens.add(new Token("(", "PARENTESIS_IZQ"));
                avanzar();
            } else if (caracterAct == ')') {
                tokens.add(new Token(")", "PARENTESIS_DER"));
                avanzar();
            } else {
                reportarError(String.valueOf(caracterAct));
                avanzar();
            }
        }
        return tokens;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos.imprimir();
    }
}
