package compiladores.Interpretes;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private static final Map<String, Object> values = new HashMap< >();

   public static boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    } 
   public static void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }

    Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

   


}
