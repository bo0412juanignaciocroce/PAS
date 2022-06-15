package es.upm.etsisi.pas.recopilacion_datos;

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

    public FirebaseNotes(){
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadNote(NotesEntity entity){
        Log.d(DebugTags.FIREBASE_STORAGE,"Upload note");
        final String jsonToSend = entity.serializeGSon();
        Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend);
        String user_uid = mFirebaseAuth.getCurrentUser().getUid();
        myRef.child(user_uid).child("Notes").push().setValue(
                MainActivity.getCurrentCipher().cifrar(jsonToSend));
    }
}
