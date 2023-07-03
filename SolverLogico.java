/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladores.Interpretes;

public class SolverLogico {

    private final Nodo nodo;
     private TablaSimbolos tabla;

    public SolverLogico(Nodo nodo) {
        this.nodo = nodo;
    }
      public SolverLogico(Nodo nodo, TablaSimbolos tabla) {
        this.nodo = nodo;
        this.tabla = tabla;
    }
      public void setTabla(TablaSimbolos tabla){
        this.tabla = tabla;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean){
            switch (n.getValue().tipo){
                case O:
                    return ((Boolean)resultadoIzquierdo || (Boolean) resultadoDerecho);
                case Y:
                    return ((Boolean)resultadoIzquierdo && (Boolean) resultadoDerecho);
            }
        }
        
        else{
            if(resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Double || resultadoIzquierdo instanceof Double  && resultadoDerecho instanceof Boolean || resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof String || resultadoIzquierdo instanceof String  && resultadoDerecho instanceof Boolean){
                System.out.println("Error de tipo de datos  ");
                System.exit(0);
            }
        }

        return null;
    }
}
