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
    private static final int MAX_VALUE = 64;

    public static final String CHAR_LIST =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    private static void initializeCharactersToValues(){
        if(base64CharactersToValues==null) {
            base64CharactersToValues = new TreeMap<>();
            base64CharactersToValues.put('A', 0);
            base64CharactersToValues.put('B', 1);
            base64CharactersToValues.put('C', 2);
            base64CharactersToValues.put('D', 3);
            base64CharactersToValues.put('E', 4);
            base64CharactersToValues.put('F', 5);
            base64CharactersToValues.put('G', 6);
            base64CharactersToValues.put('H', 7);
            base64CharactersToValues.put('I', 8);
            base64CharactersToValues.put('J', 9);
            base64CharactersToValues.put('K', 10);
            base64CharactersToValues.put('L', 11);
            base64CharactersToValues.put('M', 12);
            base64CharactersToValues.put('N', 13);
            base64CharactersToValues.put('O', 14);
            base64CharactersToValues.put('P', 15);
            base64CharactersToValues.put('Q', 16);
            base64CharactersToValues.put('R', 17);
            base64CharactersToValues.put('S', 18);
            base64CharactersToValues.put('T', 19);
            base64CharactersToValues.put('U', 20);
            base64CharactersToValues.put('V', 21);
            base64CharactersToValues.put('W', 22);
            base64CharactersToValues.put('X', 23);
            base64CharactersToValues.put('Y', 24);
            base64CharactersToValues.put('Z', 25);
            base64CharactersToValues.put('a', 26);
            base64CharactersToValues.put('b', 27);
            base64CharactersToValues.put('c', 28);
            base64CharactersToValues.put('d', 29);
            base64CharactersToValues.put('e', 30);
            base64CharactersToValues.put('f', 31);
            base64CharactersToValues.put('g', 32);
            base64CharactersToValues.put('h', 33);
            base64CharactersToValues.put('i', 34);
            base64CharactersToValues.put('j', 35);
            base64CharactersToValues.put('k', 36);
            base64CharactersToValues.put('l', 37);
            base64CharactersToValues.put('m', 38);
            base64CharactersToValues.put('n', 39);
            base64CharactersToValues.put('o', 40);
            base64CharactersToValues.put('p', 41);
            base64CharactersToValues.put('q', 42);
            base64CharactersToValues.put('r', 43);
            base64CharactersToValues.put('s', 44);
            base64CharactersToValues.put('t', 45);
            base64CharactersToValues.put('u', 46);
            base64CharactersToValues.put('v', 47);
            base64CharactersToValues.put('w', 48);
            base64CharactersToValues.put('x', 49);
            base64CharactersToValues.put('y', 50);
            base64CharactersToValues.put('z', 51);
            base64CharactersToValues.put('0', 52);
            base64CharactersToValues.put('1', 53);
            base64CharactersToValues.put('2', 54);
            base64CharactersToValues.put('3', 55);
            base64CharactersToValues.put('4', 56);
            base64CharactersToValues.put('5', 57);
            base64CharactersToValues.put('6', 58);
            base64CharactersToValues.put('7', 59);
            base64CharactersToValues.put('8', 60);
            base64CharactersToValues.put('9', 61);
            base64CharactersToValues.put('-', 62);
            base64CharactersToValues.put('_', 63);
        }
    }

    private static void inititalizeValuesToCharacters(){

        base64ValuesToCharacters = new TreeMap<>();
        base64ValuesToCharacters.put(0,'A');
        base64ValuesToCharacters.put(1,'B');
        base64ValuesToCharacters.put(2,'C');
        base64ValuesToCharacters.put(3,'D');
        base64ValuesToCharacters.put(4,'E');
        base64ValuesToCharacters.put(5,'F');
        base64ValuesToCharacters.put(6,'G');
        base64ValuesToCharacters.put(7,'H');
        base64ValuesToCharacters.put(8,'I');
        base64ValuesToCharacters.put(9,'J');
        base64ValuesToCharacters.put(10,'K');
        base64ValuesToCharacters.put(11,'L');
        base64ValuesToCharacters.put(12,'M');
        base64ValuesToCharacters.put(13,'N');
        base64ValuesToCharacters.put(14,'O');
        base64ValuesToCharacters.put(15,'P');
        base64ValuesToCharacters.put(16,'Q');
        base64ValuesToCharacters.put(17,'R');
        base64ValuesToCharacters.put(18,'S');
        base64ValuesToCharacters.put(19,'T');
        base64ValuesToCharacters.put(20,'U');
        base64ValuesToCharacters.put(21,'V');
        base64ValuesToCharacters.put(22,'W');
        base64ValuesToCharacters.put(23,'X');
        base64ValuesToCharacters.put(24,'Y');
        base64ValuesToCharacters.put(25,'Z');
        base64ValuesToCharacters.put(26,'a');
        base64ValuesToCharacters.put(27,'b');
        base64ValuesToCharacters.put(28,'c');
        base64ValuesToCharacters.put(29,'d');
        base64ValuesToCharacters.put(30,'e');
        base64ValuesToCharacters.put(31,'f');
        base64ValuesToCharacters.put(32,'g');
        base64ValuesToCharacters.put(33,'h');
        base64ValuesToCharacters.put(34,'i');
        base64ValuesToCharacters.put(35,'j');
        base64ValuesToCharacters.put(36,'k');
        base64ValuesToCharacters.put(37,'l');
        base64ValuesToCharacters.put(38,'m');
        base64ValuesToCharacters.put(39,'n');
        base64ValuesToCharacters.put(40,'o');
        base64ValuesToCharacters.put(41,'p');
        base64ValuesToCharacters.put(42,'q');
        base64ValuesToCharacters.put(43,'r');
        base64ValuesToCharacters.put(44,'s');
        base64ValuesToCharacters.put(45,'t');
        base64ValuesToCharacters.put(46,'u');
        base64ValuesToCharacters.put(47,'v');
        base64ValuesToCharacters.put(48,'w');
        base64ValuesToCharacters.put(49,'x');
        base64ValuesToCharacters.put(50,'y');
        base64ValuesToCharacters.put(51,'z');
        base64ValuesToCharacters.put(52,'0');
        base64ValuesToCharacters.put(53,'1');
        base64ValuesToCharacters.put(54,'2');
        base64ValuesToCharacters.put(55,'3');
        base64ValuesToCharacters.put(56,'4');
        base64ValuesToCharacters.put(57,'5');
        base64ValuesToCharacters.put(58,'6');
        base64ValuesToCharacters.put(59,'7');
        base64ValuesToCharacters.put(60,'8');
        base64ValuesToCharacters.put(61,'9');
        base64ValuesToCharacters.put(62,'-');
        base64ValuesToCharacters.put(63,'_');
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
