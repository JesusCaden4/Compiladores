/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladores.Interpretes;

public class SolverAritmetico {

    private final Nodo nodo;
    private TablaSimbolos tabla;

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }
       public SolverAritmetico(Nodo nodo, TablaSimbolos tabla) {
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
              if(!tabla.existeIdentificador(n.getValue().lexema)){
                    System.out.println("La variable '"+n.getValue().lexema+ "' no existe");
                    System.exit(0);
                }else{
                 return tabla.obtener(n.getValue().lexema);
            }
                    
            }
            else if(n.getValue().tipo == TipoToken.VERDADERO){
                return true;
            }else if(n.getValue().tipo == TipoToken.FALSO){
                return false;
            } 
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case MAS:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case POR:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case ENTRE:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.MAS){
                // Ejecutar la concatenación
                String concatenacion = (String) resultadoIzquierdo + (String) resultadoDerecho;
                return concatenacion;
            }
        }
        else{
            if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof Double || resultadoIzquierdo instanceof Double && resultadoDerecho instanceof String ){
                System.out.println("Error de tipos de datos, son diferentes ");
                System.exit(0);
            }
               
        }
            
        

        return null;
    }
}
