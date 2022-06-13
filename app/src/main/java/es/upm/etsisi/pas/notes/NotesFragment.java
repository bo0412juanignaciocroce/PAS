package es.upm.etsisi.pas.notes;


import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;
import es.upm.etsisi.pas.json_peliculas.PeliculasPojoResultAdapter;
import es.upm.etsisi.pas.json_peliculas.Result;


public class NotesFragment extends Fragment {
    private class FragmentCallback implements NotesCallback{
        @Override
        public void onClickElement(int position) {
            handleOnClickElement(position);
        }
    }

    private final NotesAddNewFragment notesAddNewFragment;
    private final NotesEditFragment notesEditFragment;
    private final NotesRepository repository;
    private final NotesRepositoryAdapter adapter;
    public NotesFragment(){
        NotesRoomDatabase database = NotesRoomDatabase.getDatabase(MainActivity.getContext());
        repository = new NotesRepository(database);
        adapter = new NotesRepositoryAdapter(MainActivity.getContext(), new FragmentCallback());
        /* Esto funciona permanentemente
         */
        repository.getAll().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(@Nullable final List<NotesEntity> grupos) {
                // Update the cached copy of the grupos in the adapter.
                adapter.setItems(grupos);
            }
        });
        /* SubFragments for logic separation */
        notesAddNewFragment = new NotesAddNewFragment(repository);
        notesEditFragment = new NotesEditFragment(repository);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.notes_layout, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        ImageButton b = view.findViewById(R.id.notes_layout_add_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.AddFragmentToStack(notesAddNewFragment);
            }
        });

        RecyclerView rv = view.findViewById(R.id.notes_layout_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.getContext()));
        rv.setAdapter(adapter);
    }

    public void handleOnClickElement(int pos){
        Log.d(DebugTags.FRAGMENT_TAG,"Clicked position: "+pos);
        notesEditFragment.setCurrentNotesEntity(adapter.getEntity(pos));
        MainActivity.AddFragmentToStack(notesEditFragment);
    }
}
