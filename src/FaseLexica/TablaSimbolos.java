package FaseLexica;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, InformacionSimbolo> simbolos;

    public TablaSimbolos() {
        simbolos = new HashMap<>();
    }

    public void agregar(String nombre, InformacionSimbolo info) {
        simbolos.put(nombre, info);
    }

    public boolean existe(String nombre) {
        return simbolos.containsKey(nombre);
    }

    public InformacionSimbolo obteneInformacionSimbolo(String nombre) {
        return simbolos.get(nombre);
    }

    public void imprimir() {
        for (Map.Entry<String, InformacionSimbolo> entry : simbolos.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Informacion: " + entry.getValue());
        }
    }
}
