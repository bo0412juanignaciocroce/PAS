package es.upm.etsisi.pas;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import es.upm.etsisi.pas.firebase_usuarios.AutenticacionUsuarios;
import es.upm.etsisi.pas.json_peliculas.PeliculasPojoResultAdapter;
import es.upm.etsisi.pas.json_peliculas.Result;
import es.upm.etsisi.pas.roomdb_local.UsuariosRepository;


public class MainActivity extends AppCompatActivity {
    private static Application app = null;
    private PeliculasPojoResultAdapter adapter = null;
    private List<Result> datos = null;
    private UsuariosRepository ur = null;
    private AutenticacionUsuarios au;
    private final String LOG_TAG = "MAIN";
    LogoutHanlder loginStatus = null;
    private static FragmentManager fragmentManager;

    public static Application getApp(){
        return app;
    }
    public static FragmentManager getMyFragmentManager(){return fragmentManager;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this.getApplication();
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        datos = new ArrayList<>();

        au = new AutenticacionUsuarios(this);
        loginStatus = new LogoutHanlder(findViewById(R.id.logoutButton),this);

        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,
                MainFragment.class,null) .commit();
    }

    public static void AddFragmentToStack(Fragment f){
        Log.d(DebugTags.FRAGMENT_TAG,"Adding activity to stack");
        FragmentTransaction transaction =
                MainActivity.getMyFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView,f,null)
                .addToBackStack(null)
                .show(f)
                .commit();
    }



    private class LogoutHanlder implements View.OnClickListener {
        private Button b;
        private Activity activity;

        public LogoutHanlder(Button b, Activity act){
            activity = act;
            this.b = b;
            b.setOnClickListener(this);
            b.setText(R.string.logout_button);
        }

        @Override
        public void onClick(View view) {
            Log.d(DebugTags.FRAGMENT_TAG,"Removing activity from stack");
            au.logOut();
        }
    }
}