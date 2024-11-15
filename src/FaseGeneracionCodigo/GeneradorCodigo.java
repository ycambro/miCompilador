package FaseGeneracionCodigo;

import java.util.List;

import FaseLexica.InformacionSimbolo;
import FaseLexica.TablaSimbolos;
import FaseSintactica.AST.NodoAST;
import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;
import FaseSintactica.Visitante.IVisitanteAST;

public class GeneradorCodigo implements IVisitanteAST {

    private int contadorTemporales;
    private List<String> listaTemporales;
    private TablaSimbolos tablaSimbolos;
    private List<String> codigo;

    public GeneradorCodigo() {
        this.contadorTemporales = 0;
        this.listaTemporales = new java.util.ArrayList<>();
        tablaSimbolos = new TablaSimbolos();
        codigo = new java.util.ArrayList<>();
    }

    @Override
    public void visitar(NodoOperacion nodo) {
        NodoAST izquierdo = nodo.obtenerIzquierdo();
        NodoAST derecho = nodo.obtenerDerecho();

        // Se recorren los hijos del nodo
        izquierdo.aceptar(this);
        derecho.aceptar(this);

        // Generar el código de operación en base al operador
        String operador = nodo.obtenerValor();
        if (operador.equals("=")) {
            // Asignación
            System.out.println(listaTemporales.get(listaTemporales.size()-2) + operador + "$" + listaTemporales.get(listaTemporales.size()-1));
            codigo.add(listaTemporales.get(listaTemporales.size()-2) + operador + "$" + listaTemporales.get(listaTemporales.size()-1));
            listaTemporales.removeLast();
        } else {
            // Operaciones aritméticas
            String temp1 = obtenerTemporal();
            System.out.println(temp1 + "=$((" + listaTemporales.get(listaTemporales.size()-2) + " " + operador + " " + listaTemporales.get(listaTemporales.size()-1) + "))");

            codigo.add(temp1 + "=$((" + listaTemporales.get(listaTemporales.size()-2) + " " + operador + " " + listaTemporales.get(listaTemporales.size()-1) + "))");

            // Se eliminan los dos últimos temporales y se agrega el nuevo temporal
            listaTemporales.removeLast();
            listaTemporales.removeLast();
            listaTemporales.add(temp1);
        }
    }

    @Override
    public void visitar(NodoNumero nodo) {
        // En este caso simplemente se usa el número tal cual
        String temp = obtenerTemporal();
        System.out.println(temp + "=" + nodo.obtenerValor());
        codigo.add(temp + "=" + nodo.obtenerValor());
        listaTemporales.add(temp);
    }

    @Override
    public void visitar(NodoIdentificador nodo) {
        // Para identificadores simplemente se usa el nombre
        String temp = obtenerTemporal();
        if (!tablaSimbolos.existe(nodo.obtenerValor())) {
            System.out.println(temp + "=" + nodo.obtenerValor());
            codigo.add(temp + "=" + nodo.obtenerValor());
            
            listaTemporales.add(temp);
            tablaSimbolos.agregar(nodo.obtenerValor(), new InformacionSimbolo(temp));
        } else {
            System.out.println(temp + "=$" + tablaSimbolos.obtenerInformacionSimbolo(nodo.obtenerValor()).getTemp());
            codigo.add(temp + "=$" + tablaSimbolos.obtenerInformacionSimbolo(nodo.obtenerValor()).getTemp());
            listaTemporales.add(temp);
        }
    }

    // Obtener un temporal para almacenar resultados
    private String obtenerTemporal() {
        return "t" + (contadorTemporales++);
    }

    // Obtener el código generado
    public List<String> obtenerCodigo() {
        return codigo;
    }

    // Obtener la tabla de símbolos
    public TablaSimbolos obtenerTablaSimbolos() {
        return tablaSimbolos;
    }
}