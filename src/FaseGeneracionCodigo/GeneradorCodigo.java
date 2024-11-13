package FaseGeneracionCodigo;

import java.util.List;

import FaseSintactica.AST.NodoAST;
import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;
import FaseSintactica.Visitante.IVisitanteAST;

public class GeneradorCodigo implements IVisitanteAST {

    private int contadorTemporales;
    private List<String> listaTemporales;

    public GeneradorCodigo() {
        this.contadorTemporales = 0;
        this.listaTemporales = new java.util.ArrayList<>();
    }

    // Se inicia la generación de código
    public void generarCodigo(NodoAST ast) {
        ast.aceptar(this);
        System.out.println("Confirmación [Fase Generación de Código]: Generación de código completada con éxito.");
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
        String temp1 = obtenerTemporal();
        System.out.println(temp1 + " = " + listaTemporales.get(listaTemporales.size()-2) + " " + operador + " " + listaTemporales.get(listaTemporales.size()-1));
        
        // Se eliminan los dos últimos temporales y se agrega el nuevo temporal
        listaTemporales.removeLast();
        listaTemporales.removeLast();
        listaTemporales.add(temp1);
    }

    @Override
    public void visitar(NodoNumero nodo) {
        // En este caso simplemente se usa el número tal cual
        String temp = obtenerTemporal();
        System.out.println(temp + " = " + nodo.obtenerValor());
        listaTemporales.add(temp);
    }

    @Override
    public void visitar(NodoIdentificador nodo) {
        // Para identificadores simplemente se usa el nombre
        String temp = obtenerTemporal();
        System.out.println(temp + " = " + nodo.obtenerValor());
        listaTemporales.add(temp);
    }

    // Obtener un temporal para almacenar resultados
    private String obtenerTemporal() {
        return "t" + (contadorTemporales++);
    }
}