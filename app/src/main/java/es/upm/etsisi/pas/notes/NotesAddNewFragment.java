package es.upm.etsisi.pas.notes;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import es.upm.etsisi.pas.DebugTags;
import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;
import es.upm.etsisi.pas.firebase_usuarios.FirebaseNotes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


public class NotesAddNewFragment extends Fragment {
    FirebaseNotes firebaseNotes;

    private NotesRepository repository;
    public NotesAddNewFragment(NotesRepository repository){
        this.repository = repository;
        firebaseNotes = new FirebaseNotes();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.notes_create_layout, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        EditText title = view.findViewById(R.id.notes_create_layout_title);
        EditText content = view.findViewById(R.id.notes_create_layout_content);
        ImageButton b = view.findViewById(R.id.notes_create_layout_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().length()==0){
                    return;
                }
                if(content.getText().length()==0){
                    return;
                }

                repository.insert(
                        new NotesEntity(
                                title.getText().toString(),
                                content.getText().toString()
                        )
                );

                NotesEntity newNote = new NotesEntity(
                        title.getText().toString(),
                        content.getText().toString()
                );

                title.setText("");
                content.setText("");
                MainActivity.getMyFragmentManager().popBackStackImmediate();

                Log.d(DebugTags.FIREBASE_STORAGE,"New JSON: "+newNote.serializeGSon());
                firebaseNotes.UploadNote(newNote);
            }
        });
    }
}
