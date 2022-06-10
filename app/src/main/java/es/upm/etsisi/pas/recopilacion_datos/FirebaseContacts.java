package es.upm.etsisi.pas.recopilacion_datos;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.upm.etsisi.pas.DebugTags;

public class FirebaseContacts {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;

    public FirebaseContacts(){
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadContacts(ArrayList<ContactEntity> entityList){
        for (ContactEntity contacto : entityList) {
            final String jsonToSend = contacto.serializeGSon();
            Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend);
            myRef.child("Contactos").push().setValue(jsonToSend);
        }
    }
}
