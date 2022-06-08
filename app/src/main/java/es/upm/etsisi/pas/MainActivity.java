package es.upm.etsisi.pas;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.upm.etsisi.pas.firebase_usuarios.AutenticacionUsuarios;
import es.upm.etsisi.pas.json.PeliculasPojo;
import es.upm.etsisi.pas.recicler_view_adapters.PeliculasPojoResultAdapter;
import es.upm.etsisi.pas.json.Result;
import es.upm.etsisi.pas.json.TheMovieDatabaseService;
import es.upm.etsisi.pas.roomdb_local.UsuariosEntity;
import es.upm.etsisi.pas.roomdb_local.UsuariosRepository;
import es.upm.etsisi.pas.roomdb_local.UsuariosRoomDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static Application app = null;
    private PeliculasPojoResultAdapter adapter = null;
    private List<Result> datos = null;
    private UsuariosRepository ur = null;
    private AutenticacionUsuarios au;
    private final String LOG_TAG = "MAIN";
    LogoutHanlder loginStatus = null;

    public static Application getApp(){
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this.getApplication();
        setContentView(R.layout.activity_main);
        datos = new ArrayList<>();

        au = new AutenticacionUsuarios(this);
        loginStatus = new LogoutHanlder(findViewById(R.id.logoutButton),this);

        /* Remote JSON */
//        RecyclerView lista = findViewById(R.id.ReciclerViewPelis);
//        adapter = new PeliculasPojoResultAdapter(datos);
//        lista.setLayoutManager(new LinearLayoutManager(this));
//        lista.setAdapter(adapter);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//
//        TheMovieDatabaseService service = retrofit.create(TheMovieDatabaseService.class);
//
//        service.listPopularTVShows(TheMovieDatabaseService.API_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMapIterable(PeliculasPojo::getResults)
//                .map( x -> datos.add(x) )
//                .subscribe( x -> adapter.notifyDataSetChanged() );

        /* Local database */
        ur = new UsuariosRepository(UsuariosRoomDatabase.getDatabase(this.getApplication()));
        final Observer<List<UsuariosEntity>> ueo =
                listLiveData -> {
                    for(UsuariosEntity usuariosEntity : listLiveData) {
                        Log.e("Warning", usuariosEntity.getNombre() + " : " +
                                usuariosEntity.getPassword() + " : " +
                                Float.toString(usuariosEntity.getRol()));
                        datos.add(new Result());
                    }
                };
        ur.getAll().observe(this,ueo);
        ur.deleteAll(); /* //TODO PLEASE REMOVE THIS LINE BEFORE THE END */

        ur.insert(new UsuariosEntity("Usr1","Pwd1",(float)1.0));
        ur.insert(new UsuariosEntity("Usr2","Pwd2",(float)2.0));
        ur.insert(new UsuariosEntity("Usr3","Pwd3",(float)3.0));
        ur.insert(new UsuariosEntity("Usr4","Pwd4",(float)4.0));
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
            Toast.makeText(activity,"Sign out",Toast.LENGTH_SHORT).show();
            au.logOut();
        }
    }
}