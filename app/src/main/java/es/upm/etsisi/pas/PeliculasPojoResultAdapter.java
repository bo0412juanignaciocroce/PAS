package es.upm.etsisi.pas;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

import es.upm.etsisi.myapplication.R;

public class PeliculasPojoResultAdapter extends RecyclerView.Adapter<PeliculasPojoResultAdapter
        .ViewHolder> {
    private List<Result> datos;
    public PeliculasPojoResultAdapter(List<Result> datos) {
        this.datos = datos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent ,
                false);
        return new ViewHolder(mView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(datos.get(position).getName());
        Picasso.get().load("https://www.themoviedb.org/t/p/w1280"+
                datos.get(position).getPosterPath()).resize(250,400).
                into(holder.cover);
    }
    @Override
    public int getItemCount() {
        return datos.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private ImageView cover;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            cover = itemView.findViewById(R.id.cover);
        }
    }
}