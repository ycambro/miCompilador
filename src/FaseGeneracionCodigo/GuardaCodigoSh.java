package FaseGeneracionCodigo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import FaseLexica.InformacionSimbolo;
import FaseLexica.TablaSimbolos;

public class GuardaCodigoSh {
    private BufferedWriter writer;

    public GuardaCodigoSh(List<String> codigo, TablaSimbolos tablaSimbolos) {
        try {
            writer = new BufferedWriter (new FileWriter("codigoGenerado.sh"));
            writer.write("#!/bin/bash\n\n"); // Escribir el encabezado del archivo bash
            for (String linea : codigo) {
                writer.write(linea);
                writer.newLine();
            }

            for (Map.Entry<String, InformacionSimbolo> entry : tablaSimbolos.obtenerSimbolos().entrySet()) {
                InformacionSimbolo informacion = entry.getValue();
                writer.write("echo \"El valor de " + entry.getKey() + " es: $" + informacion.getTemp() +"\"");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de salida: " + e.getMessage());
        }
    }
}
