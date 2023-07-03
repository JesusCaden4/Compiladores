package compiladores.Interpretes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sacnner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    
    
    ////////////////////////////   PALABRA RESERVADAS    ////////////////////////////////
    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPERS);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }
    
    ////////////////////////////   SIGNOS   ////////////////////////////////
    private static final Map<String, TipoToken> signos;
    static{
        signos = new HashMap<>();
        signos.put("+", TipoToken.MAS);
        signos.put(" ", TipoToken.SPACE);
        signos.put("}", TipoToken.COR_DER);
        signos.put("{", TipoToken.COR_IZ);
        signos.put(")", TipoToken.PARE_DER);
        signos.put("(", TipoToken.PARE_IZ);
        signos.put("*", TipoToken.POR);
        signos.put("/", TipoToken.ENTRE);
        signos.put("-", TipoToken.MENOS);
        signos.put("=", TipoToken.ASIGNACION);
        signos.put("==", TipoToken.IGUAL);
        signos.put("<", TipoToken.MENOR);
        signos.put(">", TipoToken.MAYOR);
        signos.put("<=", TipoToken.MENOR_O_IGUAL);
        signos.put(">=", TipoToken.MAYOR_O_IGUAL);
        signos.put("!", TipoToken.EXCLA);
        signos.put("!=", TipoToken.DIFERENTE);
        signos.put(",", TipoToken.COMA);
        signos.put(";", TipoToken.PUN_Y_COM);
    }

    ////////////////////////////   NUMEROS   ////////////////////////////////
    private static final Map<String, TipoToken> numeros;
    static{
        numeros = new HashMap<>();
        numeros.put("0", TipoToken.NUMERO);
        numeros.put("1", TipoToken.NUMERO);
        numeros.put("2", TipoToken.NUMERO);
        numeros.put("3", TipoToken.NUMERO);
        numeros.put("4", TipoToken.NUMERO);
        numeros.put("5", TipoToken.NUMERO);
        numeros.put("6", TipoToken.NUMERO);
        numeros.put("7", TipoToken.NUMERO);
        numeros.put("8", TipoToken.NUMERO);
        numeros.put("9", TipoToken.NUMERO);
        
    }
    
    Sacnner(String source){
    this.source = source;
}

List<Token> scanTokens(){
    char charac = ' ';
    String character = "";
    String sigchar = "";
    String palabra = "";
    String num = "";
    boolean finalizado = false;
    int caso = 0;

    for (int i = 0; i < source.length(); i++) {
        charac = source.charAt(i);
        character = String.valueOf(charac);
        if(charac==' '||charac=='\n'){
            
        }else if (Character.isWhitespace(charac)) {
        continue;
        }

        if (i < source.length() - 1) {
            sigchar = character + source.charAt(i + 1);
        }

        if (sigchar.equals("/*")) {
            caso = 3;
        } else if (sigchar.equals("//")) {
            caso = 4;
        } else if (signos.containsKey(character) || i == source.length() - 1) {
            caso = 2;
        } else if (charac == '"') {
            caso = 5;
        } else if (numeros.containsKey(character) && palabra.equals("")) {
            caso = 1;
        } else {
            caso = 0;
        }

        switch (caso) {
            case 0:
                if (charac == '\n') {
                    linea++;
            palabra=""; // Incrementar el contador de línea
        } else {
            palabra += character;
        }
                break;
            case 1:
                while (numeros.containsKey(character) || charac == '.') {
                    num += character;
                    i++;
                    if (i < source.length()) {
                        charac = source.charAt(i);
                        character = String.valueOf(charac);
                    } else {
                        break;
                    }
                }
                i--;
                tokens.add(new Token(TipoToken.NUMERO, num, Double.parseDouble(num), linea));
                
                num = "";
                break;
            case 2:
                if (palabrasReservadas.containsKey(palabra)) {
                    tokens.add(new Token(palabrasReservadas.get(palabra), palabra, null, linea));
                    
                    palabra = "";
                } else {
                    if (!palabra.equals("")) {
                        tokens.add(new Token(TipoToken.IDENTIFICADOR, palabra, null, linea));
                        
                        palabra = "";
                    }
                }

                if (signos.containsKey(sigchar)) {
                    tokens.add(new Token(signos.get(sigchar), sigchar, null, linea));
                    
                    i++;
                } else {
                    if (charac != ' ' && !character.equals("")) {
                        tokens.add(new Token(signos.get(character), character, null, linea));
                        
                    }
                }
                break;
            case 3:
                for (int a = i; a < source.length(); a++) {
                    charac = source.charAt(a);
                    character = String.valueOf(charac);
                    if (a < source.length() - 1) {
                        sigchar = character + source.charAt(a + 1);
                    }
                    if (charac == '\n') {
                    linea++; // Incrementar el contador de línea
                    }
                    i = a;
                    if (sigchar.equals("*/")) {
                        i = a + 1;
                        finalizado = true;
                        a=source.length();
                    }
                }
                if (!finalizado) {
                    System.out.println("No se encontró fin de comentario");
                    System.exit(1);
                }
                break;
            case 4:
                for (int a = i; a < source.length(); a++) {
                    charac = source.charAt(a);
                    character = String.valueOf(charac);
                    if (a < source.length() - 1) {
                        sigchar = character + source.charAt(a + 1);
                    }
                    if (sigchar.equals("\n") || a == source.length() - 1) {
                        i = a + 1;
                        a=source.length();
                    }
                }
                break;
            case 5:
                palabra = "";
                String cadena = "";
                cadena = cadena + '"';
                for (int a = i + 1; a < source.length(); a++) {
                    charac = source.charAt(a);
                    character = String.valueOf(charac);
                    if (charac == '"') {
                        i = a;
                        finalizado = true;
                        break;
                    } else {
                        palabra = palabra + character;
                    }
                }
                if (!finalizado) {
                    System.out.println("No se encontró el fin de la cadena");
                    System.exit(1);
                }
                cadena = cadena + palabra + '"';
                tokens.add(new Token(TipoToken.CADENA, cadena, palabra, linea));
                
                palabra = "";
                sigchar = "";
                break;
        }
    }

    if (tokens.size() >= 2) {
                // Obtener una vista de sublista que contiene los tokens a eliminar
                List<Token> tokensToRemove = tokens.subList(tokens.size() - 1, tokens.size());

                // Remover los tokens de la lista principal
                tokens.removeAll(tokensToRemove);
                linea--;
            }

    tokens.add(new Token(TipoToken.EOF, "", null, linea));
    return tokens;
}

}