package FaseLexica;
public class InformacionSimbolo {
    private int linea;
    private boolean estaDeclarado;
    private String temp;

    public InformacionSimbolo(int linea) {
        this.linea = linea;
    }

    public InformacionSimbolo(String temp) {
        this.temp = temp;
    }

    public void setEstaDeclarado(boolean estaDeclarado) {
        this.estaDeclarado = estaDeclarado;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getLinea() {
        return linea;
    }

    public boolean getEstaDeclarado() {
        return estaDeclarado;
    }

    public String getTemp() {
        return temp;
    }
}
