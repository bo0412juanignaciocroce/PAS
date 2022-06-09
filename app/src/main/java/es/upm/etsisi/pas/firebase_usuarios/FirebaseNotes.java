package es.upm.etsisi.pas.firebase_usuarios;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import es.upm.etsisi.pas.notes.NotesEntity;

public class FirebaseNotes {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;

    public FirebaseNotes(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadNote(NotesEntity entity){
        String user_uid = mFirebaseAuth.getCurrentUser().getUid();
        myRef.child(user_uid).push().setValue(entity.serializeGSon());
    }
}
