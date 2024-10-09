package FaseLexica;
public class Token {
    private String valor;
    private String atributo;
    private int linea;

    public Token(String valor, String atributo, int linea) {
        this.valor = valor;
        this.atributo = atributo;
        this.linea = linea;
    }

    public String getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    public String getAtributo() {
        return atributo;
    }

    @Override
    public String toString() {
        return "valor: " + valor + ", Tipo: " + atributo;
    }
}
