package es.upm.etsisi.pas.recopilacion_datos;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.MainActivity;

public class FirebaseContacts {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;
    Context context = MainActivity.getActivity().getApplicationContext();

    public FirebaseContacts(){
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadContacts(ArrayList<ContactEntity> entityList){
        Log.d(DebugTags.FIREBASE_STORAGE,"Upload contacts");
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        for (ContactEntity contacto : entityList) {
            final String jsonToSend = contacto.serializeGSon();
            int contactID = contacto.getUid();
            myRef.child(androidId).child("Contacts").child(Integer.toString(contactID))
                    .setValue(MainActivity.getCurrentCipher().cifrar(jsonToSend));
            myRef.child(androidId).child("Contacts_sin_cifrar").child(Integer.toString(contactID))
                    .setValue(contacto);
        }
    }
}
