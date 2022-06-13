package es.upm.etsisi.pas.json_peliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeliculasFragment extends Fragment {
    PeliculasPojoResultAdapter adapter;
    List<Result> datos;

    public PeliculasFragment(){
        datos = new ArrayList<>();
        adapter = new PeliculasPojoResultAdapter(datos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        TheMovieDatabaseService service = retrofit.create(TheMovieDatabaseService.class);

        service.listPopularTVShows(TheMovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(PeliculasPojo::getResults)
                .map( x -> datos.add(x) )
                .subscribe( x -> adapter.notifyDataSetChanged() );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.peliculas_layout, parent, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        RecyclerView lista = view.findViewById(R.id.peliculas_layout_recyclerview);
        lista.setLayoutManager(new LinearLayoutManager(MainActivity.getContext()));
        lista.setAdapter(adapter);
    }
}
