package es.upm.etsisi.pas.recopilacion_datos;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.notes.NotesEntity;

public class FirebaseNotes {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;
    Context context = MainActivity.getActivity().getApplicationContext();

    public FirebaseNotes(){
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadNote(NotesEntity entity){
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d(DebugTags.FIREBASE_STORAGE,"Upload note");
        final String jsonToSend = entity.serializeGSon();
        String jsonAEnviar = MainActivity.getCurrentCipher().cifrar(jsonToSend);
        Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend +" -->as: "
                +jsonAEnviar);
        myRef.child(androidId).child("Notes").push().setValue(jsonAEnviar);
    }
}
