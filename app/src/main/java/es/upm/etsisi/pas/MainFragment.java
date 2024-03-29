package es.upm.etsisi.pas;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.upm.etsisi.pas.json_libros.LibrosFragment;
import es.upm.etsisi.pas.json_peliculas.PeliculasFragment;
import es.upm.etsisi.pas.notes.NotesFragment;
import es.upm.etsisi.pas.utilidades_cifrado.FragmentCipherKey;

public class MainFragment extends Fragment {

    enum funcionalidadesDisponiblesIDs {
        Notas,
        Peliculas,
        Libros,
        Contraseña
    }

    private class FuncionalidadesDisponibles{
        public final funcionalidadesDisponiblesIDs mid;
        public final String nombre;
        public final Fragment fragment;

        private FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs mid,
                                           String nombre, Fragment fragment) {
            this.mid = mid;
            this.nombre = nombre;
            this.fragment = fragment;
        }
    }

    private class FuncionalidadesDisponiblesAdapter extends RecyclerView.Adapter<
            FuncionalidadesDisponiblesAdapter.ViewHolder>{
        private final List<FuncionalidadesDisponibles> datos;

        public FuncionalidadesDisponiblesAdapter(
                List<FuncionalidadesDisponibles> funcionalidadesDisponibles) {
            datos = funcionalidadesDisponibles;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.recyclerview_row_button, parent , false);
            return new FuncionalidadesDisponiblesAdapter.ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.nombre.setText(datos.get(position).nombre);
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final Button nombre;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.recyclerview_row_button_button);
                itemView.setOnClickListener(this);
                nombre.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                final int position = getAdapterPosition();
                cambiarDeFragment(position);
            }
        }

        protected void cambiarDeFragment(int position){
            MainActivity.AddFragmentToStack(datos.get(position).fragment);
        }
    }

    final FuncionalidadesDisponibles[] arrayFuncionalidades = {
            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Notas,
                    "Notas", new NotesFragment()),
            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Peliculas,
                    "Peliculas", new PeliculasFragment()),
            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Libros,
                    "Libros", new LibrosFragment()),
            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Contraseña,
                    "Contraseña", new FragmentCipherKey()),
    };

    FuncionalidadesDisponiblesAdapter funcionalidadesDisponiblesAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.main_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        Log.d(DebugTags.FRAGMENT_TAG,"Cantidad en lista: "+arrayFuncionalidades.length);
        funcionalidadesDisponiblesAdapter = new FuncionalidadesDisponiblesAdapter(
                new ArrayList<>(Arrays.asList(arrayFuncionalidades)));

        RecyclerView rv = view.findViewById(R.id.mainRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(funcionalidadesDisponiblesAdapter);
        funcionalidadesDisponiblesAdapter.notifyDataSetChanged();
        Log.d(DebugTags.FRAGMENT_TAG,"Cantidad en adapter: "+funcionalidadesDisponiblesAdapter.getItemCount());
        if(!MainActivity.isCipherKeyInitialized()){
            //Fragment de pedir contraseña
            MainActivity.AddFragmentToStack(arrayFuncionalidades[3].fragment);
        }
    }
}
