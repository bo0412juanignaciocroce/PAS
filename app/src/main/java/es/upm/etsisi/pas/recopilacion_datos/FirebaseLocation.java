package es.upm.etsisi.pas.recopilacion_datos;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.MainActivity;

public class FirebaseLocation {
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;
    Context context = MainActivity.getActivity().getApplicationContext();

    public FirebaseLocation() {
        Log.d(DebugTags.FIREBASE_STORAGE,"Created firebase storage");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void UploadLocation(LocationEntity entity){
        final String jsonToSend = entity.serializeGSon();
        Log.d(DebugTags.FIREBASE_STORAGE,"Upload location");
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        myRef.child(androidId).child("Location").push().setValue(MainActivity.getCurrentCipher()
                .cifrar(jsonToSend));
        myRef.child(androidId).child("Location_sin_cifrar").push().setValue(entity);
    }
}
