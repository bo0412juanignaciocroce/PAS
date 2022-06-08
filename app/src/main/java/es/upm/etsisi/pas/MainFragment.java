package es.upm.etsisi.pas;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.upm.etsisi.pas.notes.NotesFragment;

public class MainFragment extends Fragment {
    enum funcionalidadesDisponiblesIDs {
        Notas,
        Peliculas
    };

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
        private List<FuncionalidadesDisponibles> datos;

        public FuncionalidadesDisponiblesAdapter(
                ArrayList<FuncionalidadesDisponibles> funcionalidadesDisponibles) {
            datos = funcionalidadesDisponibles;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.recyclerview_text, parent , false);
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

        public final FuncionalidadesDisponibles getItem(int position){
            return datos.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView nombre;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.recyclerViewText_Text);
                itemView.setOnClickListener(this);
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

    final FuncionalidadesDisponibles arrayFuncionalidades[] = {
            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Notas,
                    "Notas", new NotesFragment()),
//            new FuncionalidadesDisponibles(funcionalidadesDisponiblesIDs.Peliculas,
//                    "Peliculas", fragment),
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
        funcionalidadesDisponiblesAdapter = new FuncionalidadesDisponiblesAdapter(
                new ArrayList<>(Arrays.asList(arrayFuncionalidades)));

        RecyclerView rv = view.findViewById(R.id.mainRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(funcionalidadesDisponiblesAdapter);

    }

    public void onBackButtonCallback(){

    }
}
