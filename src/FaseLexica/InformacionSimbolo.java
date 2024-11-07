package FaseLexica;
public class InformacionSimbolo {
    private int linea;
    private boolean estaDeclarado;

    public InformacionSimbolo(int linea) {
        this.linea = linea;
    }

    public void setEstaDeclarado(boolean estaDeclarado) {
        this.estaDeclarado = estaDeclarado;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getLinea() {
        return linea;
    }

    public boolean getEstaDeclarado() {
        return estaDeclarado;
    }
}
