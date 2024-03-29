package es.upm.etsisi.pas;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;

import es.upm.etsisi.pas.firebase_usuarios.AutenticacionUsuarios;
import es.upm.etsisi.pas.recopilacion_datos.RequestPermissions;
import es.upm.etsisi.pas.recopilacion_datos.RetrieveContacts;
import es.upm.etsisi.pas.recopilacion_datos.RetrieveLocation;
import es.upm.etsisi.pas.utilidades_cifrado.CifradoVigenere;
import es.upm.etsisi.pas.utilidades_cifrado.Cifrador;


public class MainActivity extends AppCompatActivity {
    private static String EMPTY_STRING = "";
    private static Application app = null;
    private static FragmentManager fragmentManager;
    private static Context context;
    private static Activity activity;
    private static SharedPreferences sharedPreferences;
    private AutenticacionUsuarios au;
    private final String LOG_TAG = "MAIN";
    LogoutHanlder loginStatus = null;
    private static RetrieveContacts rc;
    private static RetrieveLocation rl;
    private static String cipherKey;
    private static Cifrador cifrador = null;

    public static Application getApp(){
        return app;
    }
    public static FragmentManager getMyFragmentManager(){return fragmentManager;}
    public static Context getContext() { return context; }
    public static Context getActivity() { return activity; }
    public static SharedPreferences getSharedPreferences() { return sharedPreferences;}

    public static void updateCipherKey(){
        String newCipherKey = sharedPreferences.getString(
                activity.getString(R.string.cipherKey),EMPTY_STRING);
        if(cipherKey==null || !cipherKey.equals(newCipherKey)){
            cipherKey = newCipherKey;
            cifrador = new CifradoVigenere(cipherKey);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this.getApplication();
        fragmentManager = getSupportFragmentManager();
        context = this.getApplicationContext();
        activity = this;
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        updateCipherKey();
        final String clave = "KEY";
        Cifrador c = new CifradoVigenere(clave);
        final String original = "Prueba_de_texto_a_cifrar";
        final String cifrado = c.cifrar(original);
        final String descifrado = c.descifrar(cifrado);
        Log.d(DebugTags.CIFRADOR, "Cifrador Vignere key: "+clave+" original: " + original
                + " cifrado: " + cifrado + " descifrado: "+descifrado);
        Boolean requestedPermissions = RequestPermissions.requestPermissions(context,activity);
        Log.d(DebugTags.MAIN_EXECUTION,"onCreate finish");
        // In case it is not required to ask for permissions
        if(!requestedPermissions){
            startAfterPermissions();
        }
    }

    private void startAfterPermissions(){
        setContentView(R.layout.activity_main);
        LocationManager mgr = (LocationManager) getSystemService(LOCATION_SERVICE);

        rl = new RetrieveLocation(mgr);

        ContentResolver cr = getContentResolver();
        rc = new RetrieveContacts(cr);

        au = new AutenticacionUsuarios(this);
        loginStatus = new LogoutHanlder(findViewById(R.id.logoutButton),this);

        rl.uploadLocation(rl.getLocation());
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,
                MainFragment.class,null) .commit();
        Log.d(DebugTags.MAIN_EXECUTION,"startAfterPermissions");
    }

    public static void AddFragmentToStack(Fragment f){
        Log.d(DebugTags.MAIN_EXECUTION,"Added fragment to stack");
        Log.d(DebugTags.FRAGMENT_TAG,"Adding activity to stack");
        FragmentTransaction transaction =
                MainActivity.getMyFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView,f,null)
                .addToBackStack(null)
                .show(f)
                .commit();
    }



    private class LogoutHanlder implements View.OnClickListener {

        public LogoutHanlder(Button b, Activity act){
            b.setOnClickListener(this);
            b.setText(R.string.logout_button);
        }

        @Override
        public void onClick(View view) {
            Log.d(DebugTags.MAIN_EXECUTION,"Logged out");
            au.logOut();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean volverPedir = false;
        for(int i = 0;i<permissions.length;i++){
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                volverPedir = true;
                break;
            }
        }
        if(volverPedir){
            ArrayList<String> permissionsFailed =
                    RequestPermissions.shouldAskPermissionsFailures(this);
            if(permissionsFailed.size()>0){
                /* Application fail, missing permissions */
                Toast.makeText(this.getApplicationContext(),permissionsFailed.toString(),Toast.LENGTH_LONG).show();
                this.finish();
            }
            RequestPermissions.requestPermissions(context,activity);
        }else{
            /* Todos permisos conseguidos */
            startAfterPermissions();
        }
        Log.d(DebugTags.MANIFEST_PERMISSIONS,"MAIN recept"+ Arrays.toString(permissions) +" :results: "  +Arrays.toString(grantResults));
        Log.d(DebugTags.MAIN_EXECUTION,"Permissions completed");
    }

    @Override
    public void onBackPressed() {
        updateCipherKey();
        if(!cipherKey.equals(EMPTY_STRING)){
            //Only allow interactions if cipherKey is valid
            super.onBackPressed();
        }
        Log.d(DebugTags.MAIN_EXECUTION,"BACK BUTTON PRESSED");
    }

    public static boolean isCipherKeyInitialized(){
        return !cipherKey.equals(EMPTY_STRING);
    }

    public static void popBackStack(){
        fragmentManager.popBackStackImmediate();
    }

    public static Cifrador getCurrentCipher(){
        return cifrador;
    }
}