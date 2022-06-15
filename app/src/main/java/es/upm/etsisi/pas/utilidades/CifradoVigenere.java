package es.upm.etsisi.pas.utilidades;

import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CifradoVigenere implements Cifrador{
    private final Iterable<Character> keyIterable;
    //RFC 4648 following https://www.ietf.org/rfc/rfc4648.txt
    private static Map<Character,Integer> base64CharactersToValues = null;
    private static Map<Integer,Character> base64ValuesToCharacters = null;

    public static final String CHAR_LIST =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    private static final int MAX_VALUE = CHAR_LIST.length();

    private static void initializeCharactersToValues(){
        if(base64CharactersToValues==null) {
            base64CharactersToValues = new TreeMap<>();
            for(int i = 0;i<CHAR_LIST.length();i++){
                base64CharactersToValues.put(CHAR_LIST.charAt(i), i);
            }
        }
    }

    private static void inititalizeValuesToCharacters(){
        if(base64ValuesToCharacters == null) {
            base64ValuesToCharacters = new TreeMap<>();
            for(int i = 0;i<CHAR_LIST.length();i++){
                base64ValuesToCharacters.put(i,CHAR_LIST.charAt(i));
            }
        }
    }

    private static void initializeMap(){
        initializeCharactersToValues();
        inititalizeValuesToCharacters();
    }

    public CifradoVigenere(String key){
        initializeMap();
        keyIterable = Iterables.cycle(key.chars().mapToObj(c -> (char)c)
                .collect(Collectors.toList()));
    }

    private interface Operation{
        Integer operation(Integer stringValue, Integer keyValue);
    }

    private String vigenereOperation(final String s, final Operation o){
        char cifrado[] = new char[s.length()];
        Iterator<Character> currentKeyIterator = keyIterable.iterator();
        int position = 0;
        for(final char c : s.toCharArray()){
            if(c == ' ' || c == '\0'){
                // Not process spaces
                continue;
            }
            final Integer valorFuente = base64CharactersToValues.get(c);
            final Character k = currentKeyIterator.next();
            final Integer valorKey = base64CharactersToValues.get(k);
            final int valorResultado = Math.floorMod(o.operation(valorFuente,valorKey),MAX_VALUE);
            final Character resultado = base64ValuesToCharacters.get(valorResultado);
            cifrado[position++]= resultado;
        }
        //cifrado[position]='\0'; //End of string
        return new String(cifrado);
    }


    @Override
    public String cifrar(String crudo) {
        return vigenereOperation(crudo, Integer::sum);
    }

    @Override
    public String descifrar(String cifrado) {
        return vigenereOperation(cifrado, new Operation() {
            @Override
            public Integer operation(Integer stringValue, Integer keyValue) {
                return stringValue - keyValue;
            }
        });
    }
}
