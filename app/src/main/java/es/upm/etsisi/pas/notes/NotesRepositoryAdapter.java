package es.upm.etsisi.pas.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.upm.etsisi.pas.R;
import es.upm.etsisi.pas.json_peliculas.PeliculasPojoResultAdapter;
import es.upm.etsisi.pas.json_peliculas.Result;

public class NotesRepositoryAdapter extends RecyclerView.Adapter<NotesRepositoryAdapter
        .ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView userItemView;

        private ViewHolder(View itemView) {
            super(itemView);
            userItemView = itemView.findViewById(R.id.recyclerViewText_Text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getAdapterPosition();
            cb.onClickElement(position);
        }
    }

    private NotesCallback cb;
    private final LayoutInflater mInflater;
    private List<NotesEntity> itemsList;

    /**
     * Constructor
     *
     * @param context context
     */
    public NotesRepositoryAdapter(Context context,NotesCallback cb) {
        this.cb = cb;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_row_text, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (itemsList != null) {
            NotesEntity current = itemsList.get(position);
            holder.userItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.userItemView.setText("No item");
        }
    }

    public void setItems(List<NotesEntity> userList){
        itemsList = userList;
        notifyDataSetChanged();
    }

    public NotesEntity getEntity(int pos){
        return itemsList.get(pos);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return (itemsList == null)
                ? 0
                : itemsList.size();
    }

    public NotesEntity getGrupoAtPosition (int position) {
        return itemsList.get(position);
    }
}
