package es.upm.etsisi.pas.firebase_usuarios;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import es.upm.etsisi.pas.DebugTags;
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
        final String jsonToSend = entity.serializeGSon();
        Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend);
        String user_uid = mFirebaseAuth.getCurrentUser().getUid();
        myRef.child(user_uid).push().setValue(jsonToSend);
    }
}
