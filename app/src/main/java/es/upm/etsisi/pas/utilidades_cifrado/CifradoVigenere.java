package es.upm.etsisi.pas.utilidades_cifrado;

import android.util.Log;

import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.recopilacion_datos.SerializableEntity;

public class CifradoVigenere implements Cifrador{
    private final Iterable<Character> keyIterable;
    private final int keyLenght;
    private static Map<Character,Integer> base64CharactersToValues = null;
    private static Map<Integer,Character> base64ValuesToCharacters = null;


    public static final String CHAR_ALLOWED_WITHOUT_CIPHER = "'\"{}:,. \n\0\\";
    //RFC 4648 following https://www.ietf.org/rfc/rfc4648.txt
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
        keyLenght = key.length();
        keyIterable = Iterables.cycle(key.chars().mapToObj(c -> (char)c)
                .collect(Collectors.toList()));
    }

    private interface Operation{
        Integer operation(Integer stringValue, Integer keyValue);
    }

    private String vigenereOperation(final String s, final Operation o){
        if(s == null || s.length()==0 || keyLenght==0 || o == null){
            Log.d(DebugTags.CIFRADOR,"NO DATA!");
            return null;
        }
        char[] cifrado = new char[s.length()];
        Iterator<Character> currentKeyIterator = keyIterable.iterator();
        int position = 0;
        for(final char c : s.toCharArray()){
            final Integer valorFuente = base64CharactersToValues.get(c);
            Character resultado;
            //Si el carácter no está en el juego
            if(valorFuente != null) {
                final Character k = currentKeyIterator.next();
                final Integer valorKey = base64CharactersToValues.get(k);
                final int valorResultado = Math.floorMod(o.operation(valorFuente,valorKey),MAX_VALUE);
                resultado = base64ValuesToCharacters.get(valorResultado);
            }else{
                if (CHAR_ALLOWED_WITHOUT_CIPHER.indexOf(c) != -1) {
                    resultado = c;
                }else{
                    Log.d(DebugTags.CIFRADOR,"Fallo de caŕacter: '"+c+"'");
                    throw new IllegalArgumentException("Character out of valid ranges: '"+c+"'");
                }
            }
            cifrado[position++]= resultado;
        }
        //cifrado[position]='\0'; //End of string
        return new String(cifrado);
    }


    @Override
    public String cifrar(SerializableEntity entity) {
        if(entity == null){
            Log.d(DebugTags.CIFRADOR,"NO DATA!");
            return null;
        }
        return cifrar(entity.serializeGSon());
    }

    @Override
    public String cifrar(String crudo) {
        if(crudo==null){
            Log.d(DebugTags.CIFRADOR,"NO STRING DATA CIFRAR!");
            return null;
        }
        return vigenereOperation(crudo, Integer::sum);
    }

    @Override
    public String descifrar(String cifrado) {
        if(cifrado==null){
            Log.d(DebugTags.CIFRADOR,"NO STRING DATA DESCIFRAR!");
            return null;
        }
        return vigenereOperation(cifrado, new Operation() {
            @Override
            public Integer operation(Integer stringValue, Integer keyValue) {
                return stringValue - keyValue;
            }
        });
    }
}
