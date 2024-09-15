package FaseLexica;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, InformacionSimbolo> simbolos;

    public TablaSimbolos() {
        simbolos = new HashMap<>();
    }

    // Agrega un simbolo a la tabla de simbolos
    public void agregar(String nombre, InformacionSimbolo info) {
        simbolos.put(nombre, info);
    }

    // Modifica un simbolo en la tabla de simbolos
    public void modificar(String nombre, InformacionSimbolo info) {
        simbolos.replace(nombre, info);
    }

    // Verifica si un simbolo existe en la tabla de simbolos
    public boolean existe(String nombre) {
        return simbolos.containsKey(nombre);
    }

    // Obtiene la informacion de un simbolo
    public InformacionSimbolo obtenerInformacionSimbolo(String nombre) {
        return simbolos.get(nombre);
    }

    // Imprime la tabla de simbolos
    public void imprimir() {
        for (Map.Entry<String, InformacionSimbolo> entry : simbolos.entrySet()) {
            InformacionSimbolo informacion = entry.getValue();
            System.out.println("Nombre: " + entry.getKey() + "\nInformacion: " + "\n - Linea: " + informacion.getLinea());
        }
    }
}
