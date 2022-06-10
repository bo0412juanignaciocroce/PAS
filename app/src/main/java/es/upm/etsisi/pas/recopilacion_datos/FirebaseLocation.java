package es.upm.etsisi.pas.recopilacion_datos;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.upm.etsisi.pas.DebugTags;

public class FirebaseLocation {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;

    public FirebaseLocation() {
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadLocation(LocationEntity entity){
//        final String jsonToSend = entity.serializeGSon();
//        Log.d(DebugTags.FIREBASE_STORAGE,"New entity being sent: "+jsonToSend);
        String user_uid = mFirebaseAuth.getCurrentUser().getUid();
//        myRef.child(user_uid).push().setValue(jsonToSend);
        myRef.child(user_uid).push().setValue(entity);
    }
}
