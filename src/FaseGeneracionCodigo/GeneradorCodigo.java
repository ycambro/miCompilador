package FaseGeneracionCodigo;

import FaseSintactica.AST.NodoAST;
import FaseSintactica.AST.NodoIdentificador;
import FaseSintactica.AST.NodoNumero;
import FaseSintactica.AST.NodoOperacion;
import FaseSintactica.Visitante.IVisitanteAST;

public class GeneradorCodigo implements IVisitanteAST {

    private int contadorTemporales;

    public GeneradorCodigo() {
        this.contadorTemporales = 0;
    }

    // Se inicia la generación de código
    public void generarCodigo(NodoAST ast) {
        ast.aceptar(this);
        System.out.println("Generación de código completada con éxito.");
    }

    @Override
    public void visitar(NodoOperacion nodo) {
        NodoAST izquierdo = nodo.getIzquierdo();
        NodoAST derecho = nodo.getDerecho();

        // Se recorren los hijos del nodo
        izquierdo.aceptar(this);
        derecho.aceptar(this);

        // Generar el código de operación en base al operador
        String operador = nodo.getOperador();
        String temp1 = obtenerTemporal();
        System.out.println(temp1 + " = " + izquierdo.toString() + " " + operador + " " + derecho.toString());
    }

    @Override
    public void visitar(NodoNumero nodo) {
        // En este caso simplemente se usa el número tal cual
        String temp = obtenerTemporal();
        System.out.println(temp + " = " + nodo.getValor());
    }

    @Override
    public void visitar(NodoIdentificador nodo) {
        // Para identificadores simplemente se usa el nombre
        String temp = obtenerTemporal();
        System.out.println(temp + " = " + nodo.getNombre());
    }

    // Obtener un temporal para almacenar resultados
    private String obtenerTemporal() {
        return "t" + (contadorTemporales++);
    }
}