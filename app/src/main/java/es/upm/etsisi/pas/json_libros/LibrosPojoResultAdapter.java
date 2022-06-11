package es.upm.etsisi.pas.json_libros;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.upm.etsisi.pas.R;

public class LibrosPojoResultAdapter extends RecyclerView.Adapter<LibrosPojoResultAdapter.ViewHolder> {
    private List<Work> datos;
    public LibrosPojoResultAdapter(List<Work> datos) {
        this.datos = datos;
    }
    @NonNull
    @Override
    public LibrosPojoResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.libros_recyclerview_row_image_text, parent ,
                false);
        return new LibrosPojoResultAdapter.ViewHolder(mView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if((datos.get(position).getAuthorName() != null) && (datos.get(position).getCoverEditionKey() != null)) {
            holder.titulo.setText(datos.get(position).getTitle());
            holder.autor.setText(datos.get(position).getAuthorName().get(0));
            String cover_url = "https://covers.openlibrary.org/b/olid/" + datos.get(position).getCoverEditionKey() + "-M.jpg";
            Picasso.get().load(cover_url).resize(250,400).into(holder.cover);
        }

    }
    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView autor;
        private ImageView cover;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.nombre_libros);
            autor = itemView.findViewById(R.id.autor_libros);
            cover = itemView.findViewById(R.id.cover_libros);
        }
    }
}
