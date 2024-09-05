package FaseLexica;
public class Token {
    private String valor;
    private String atributo;

    public Token(String valor, String atributo) {
        this.valor = valor;
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public String getAtributo() {
        return atributo;
    }

    @Override
    public String toString() {
        return "valor: " + valor + ", Tipo: " + atributo;
    }
}
