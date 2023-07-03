/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladores.Interpretes;


import java.util.ArrayList;
import java.util.List;
import compiladores.Interpretes.TablaSimbolos;
public class Arbol {
    private final Nodo raiz;
   

    public Arbol(Nodo raiz){
        this.raiz = raiz;
      
    }

    public void recorrer(){
        TablaSimbolos tabla = new TablaSimbolos();
        if(raiz.getHijos()!=null){
            
        
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                    SolverAritmetico solverMAS = new SolverAritmetico(n);
                    
                    Object resMAS = solverMAS.resolver();
                    System.out.println(resMAS);
                case MENOS:
                    SolverAritmetico solverMENOS = new SolverAritmetico(n);
                    Object resMENOS = solverMENOS.resolver();
                    System.out.println(resMENOS);
                case POR:
                    SolverAritmetico solverPOR = new SolverAritmetico(n);
                    Object resPOR = solverPOR.resolver();
                    System.out.println(resPOR);
                case ENTRE:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                case MAYOR:
                    SolverRelacional solvmay = new SolverRelacional(n);
                    Object bulmay = solvmay.resolver();
                    System.out.println(bulmay);
                case MENOR:
                    SolverRelacional solvmen = new SolverRelacional(n);
                    Object bulmen = solvmen.resolver();
                case MAYOR_O_IGUAL:
                    SolverRelacional solvmayo = new SolverRelacional(n);
                    Object bulmayo = solvmayo.resolver();
                case MENOR_O_IGUAL:
                    SolverRelacional solv = new SolverRelacional(n);
                    Object bul = solv.resolver();
                    break;
                case Y:
                    SolverRelacional solvy = new SolverRelacional(n);
                    Object buly = solvy.resolver();
                    break;
                case O:
                    SolverRelacional solvo = new SolverRelacional(n);
                    Object bulo = solvo.resolver();
                    break;
               case VAR:
                   
                 
                    // Crear una variable. Usar tabla de simbolos+-

                   if(tabla.existeIdentificador(n.getHijos().get(0).getValue().lexema)){
                       System.out.println("La variable " + n.getHijos().get(0).getValue().lexema+" ya ha sido declarada anteriormente1");
                       
                   }else if(n.getHijos().size()>1){
                        Nodo resultado = n.getHijos().get(1);
                        SolverAritmetico solver2 = new SolverAritmetico(resultado);
                        solver2.setTabla(tabla);
                        Object result = solver2.resolver();                       
                        tabla.asignar(n.getHijos().get(0).getValue().lexema,result );
                           
                       }else{
                           tabla.asignar(n.getHijos().get(0).getValue().lexema,null );
                           
                       }
                    
                   

                    
                    break;


                
                
                
                case IMPRIMIR:
                     Nodo nodoExpresion = n.getHijos().get(0);

                SolverAritmetico solverImpresion = new SolverAritmetico(nodoExpresion, tabla);
                solverImpresion.setTabla(tabla);
                Object resultadoImpresion = solverImpresion.resolver();

                System.out.println(resultadoImpresion);
         
                       break;
                case SI:
                    
                       Nodo Si = n.getHijos().get(0);
                       SolverRelacional solverSi = new SolverRelacional (Si,tabla);
                      
                 
                       solverSi.setTabla(tabla);
                  
                       Object resultadoSi = solverSi.resolver();
                      
                          
                           if((Boolean)resultadoSi ){
                           Arbol sentenciaSi = new Arbol(n);
                           sentenciaSi.recorrer();
                           
                           }
                           
                          
                       
                       
                       
                    break;
                case PARA:
                    break;
                case MIENTRAS:
                    break;
                

            }
        }}
    }}
    
    
    


