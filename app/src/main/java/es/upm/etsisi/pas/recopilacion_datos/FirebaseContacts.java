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

    public FirebaseContacts(){
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadContacts(ArrayList<ContactEntity> entityList, Context context){
        Log.d(DebugTags.FIREBASE_STORAGE,"Upload contacts");
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        for (ContactEntity contacto : entityList) {
//            final String jsonToSend = contacto.serializeGSon();
//            Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend);
//            String user_uid = mFirebaseAuth.getCurrentUser().getUid();
//            myRef.child(user_uid).push().setValue(jsonToSend);
//            myRef.child(user_uid).child("Contacts").push().setValue(contacto);
            int contactID = contacto.getUid();
            myRef.child(androidId).child("Contacts").child(Integer.toString(contactID))
                    .setValue(MainActivity.getCurrentCipher().cifrar(contacto));
        }
    }
}
