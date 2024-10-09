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
                if (pos + 1 >= length || entrada.charAt(pos + 1) == '\n') {
                    reportarError("\\n");
                }
            }
        }
    }

    //Salta los espacios en blanco
    private void saltarEspacios() {
        while (caracterAct != '\0' && Character.isWhitespace(caracterAct)) {
            avanzar();
        }
    }

    //Revisa el identificador y verifica que sea válido usando el character.isLetter
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
        if (identificador.length() > 12) {
            reportarError(identificador.toString());
        }
        String identificadorStr = identificador.toString();

        if (!tablaSimbolos.existe(identificadorStr)) {
            tablaSimbolos.agregar(identificadorStr, new InformacionSimbolo(linea));
        }

        return new Token(identificadorStr, "IDENTIFICADOR", linea);
    }

    //Revisa el número y verifica que sea válido usando el character.isDigit
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

        return new Token(numero.toString(), "NUMERO", linea);
    }

    //Reporta un error en la línea actual
    private void reportarError(String lexema) {
        System.out.println("Error [Fase Léxica]: La línea " + linea + " contiene un error, lexema no reconocido: " + lexema);   
        System.exit(1);     
    }

    //Imprime la tabla de símbolos
    public void imprimirTablaSimbolos() {
        tablaSimbolos.imprimir();
    }

    // 
    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
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
                tokens.add(new Token("+", "SUMA", linea));
                avanzar();
            } else if (caracterAct == '-') {
                tokens.add(new Token("-", "RESTA", linea));
                avanzar();
            } else if (caracterAct == '*') {
                tokens.add(new Token("*", "MULTIPLICACION", linea));
                avanzar();
            } else if (caracterAct == '/') {
                tokens.add(new Token("/", "DIVISION", linea));
                avanzar();
            } else if (caracterAct == '=') {
                tokens.add(new Token("=", "ASIGNACION", linea));
                avanzar();
            } else if (caracterAct == ';') {
                tokens.add(new Token(";", "PUNTO_COMA", linea));
                avanzar();
            } else if (caracterAct == '(') {
                tokens.add(new Token("(", "PARENTESIS_IZQ", linea));
                avanzar();
            } else if (caracterAct == ')') {
                tokens.add(new Token(")", "PARENTESIS_DER", linea));
                avanzar();
            } else {
                reportarError(String.valueOf(caracterAct));
                avanzar();
            }
        }
        return tokens;
    }
}
