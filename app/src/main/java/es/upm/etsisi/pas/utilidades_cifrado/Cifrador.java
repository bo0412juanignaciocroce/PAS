package es.upm.etsisi.pas.utilidades_cifrado;

import es.upm.etsisi.pas.recopilacion_datos.SerializableEntity;

public interface Cifrador {
    String cifrar(SerializableEntity entity);
    String cifrar(String crudo);
    String descifrar(String cifrado);
}

