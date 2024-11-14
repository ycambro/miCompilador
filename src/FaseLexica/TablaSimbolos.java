package FaseLexica;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
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

    // Marca un simbolo como declarado
    public void declarar(String nombre) {
        InformacionSimbolo info = simbolos.get(nombre);
        info.setEstaDeclarado(true);
        simbolos.replace(nombre, info);
    }

    // Cambia la linea de un simbolo si se re declaro en otra linea
    public void cambiarLinea(String nombre, int linea) {
        InformacionSimbolo info = simbolos.get(nombre);
        info.setLinea(linea);
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

    // Elimina un simbolo de la tabla de simbolos
    public void eliminar(String nombre) {
        simbolos.remove(nombre);
    }

    public Map<String, InformacionSimbolo> obtenerSimbolos() {
        return simbolos;
    }

    // Imprime la tabla de simbolos
    public void imprimir() {
        for (Map.Entry<String, InformacionSimbolo> entry : simbolos.entrySet()) {
            InformacionSimbolo informacion = entry.getValue();
            System.out.println("Nombre: " + entry.getKey() + "\nInformacion: " + "\n - Linea: " + informacion.getLinea());
        }
    }

    // Guarda la tabla de simbolos en un archivo
    public void guardarEnArchivo(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Map.Entry<String, InformacionSimbolo> entry : simbolos.entrySet()) {
                InformacionSimbolo informacion = entry.getValue();
                writer.write("Nombre: " + entry.getKey() + "\nInformacion: \n - Linea: " + informacion.getLinea() + "\n");
                writer.write("\n"); // Agrega una línea en blanco entre cada símbolo
            }
        } catch (IOException e) {
            System.out.println("Error al guardar la tabla de símbolos en el archivo " + nombreArchivo);
        }
    }

    // Carga la tabla de simbolos desde un archivo
    public void cargarDesdeArchivo(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            String nombre = null;
            int lineaSimbolo = -1;

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("Nombre: ")) {
                    nombre = linea.substring(8); // Extrae el nombre saltandose la etiqueta "Nombre: "
                } else if (linea.startsWith(" - Linea: ")) {
                    lineaSimbolo = Integer.parseInt(linea.substring(10)); // Extrae el número de línea después de " - Linea: "
                    if (nombre != null && lineaSimbolo != -1) {

                        // Crea un nuevo símbolo y lo agrega a la tabla
                        agregar(nombre, new InformacionSimbolo(lineaSimbolo));
                        nombre = null; // Reinicia los valores para el siguiente símbolo
                        lineaSimbolo = -1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar la tabla de símbolos desde el archivo " + nombreArchivo);
        }
    }
}
