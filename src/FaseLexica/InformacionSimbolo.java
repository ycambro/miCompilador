package FaseLexica;
public class InformacionSimbolo {
    private int direccion;

    public InformacionSimbolo(int direccion) {
        this.direccion = direccion;
    }

    public int getLinea() {
        return direccion;
    }
}
