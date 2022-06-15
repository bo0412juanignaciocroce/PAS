package es.upm.etsisi.pas.notes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;
import es.upm.etsisi.pas.recopilacion_datos.FirebaseNotes;


public class NotesEditFragment extends Fragment {
    FirebaseNotes firebaseNotes;
    private final NotesRepository repository;
    private NotesEntity notesEntity;

    private EditText title;
    private EditText content;

    public NotesEditFragment(NotesRepository repository){
        this.repository = repository;
        firebaseNotes = new FirebaseNotes();
    }

    public void setCurrentNotesEntity(NotesEntity entity){
        notesEntity = entity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.notes_edit_layout, parent, false);
    }

    private void setContent(){
        /* Set data to be displayed */
        title.setText(notesEntity.getTitle());
        content.setText(MainActivity.getCurrentCipher().descifrar(notesEntity.getContent()));
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        title = view.findViewById(R.id.notes_edit_layout_title);
        content = view.findViewById(R.id.notes_edit_layout_content);
        ImageButton imageButtonAdd = view.findViewById(R.id.notes_edit_layout_button_add);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().length()==0){
                    return;
                }
                if(content.getText().length()==0){
                    return;
                }
                notesEntity.setTitle(title.getText().toString());
                notesEntity.setContent(MainActivity.getCurrentCipher().cifrar(content.getText().toString()));
                /* On edit, before removing from current edit, upload to firebase */
                firebaseNotes.UploadNote(notesEntity);
                repository.update( notesEntity );
                notesEntity=null;
                title.setText("");
                content.setText("");
                MainActivity.getMyFragmentManager().popBackStackImmediate();
            }
        });
        ImageButton imageButtonDelete = view.findViewById(R.id.notes_edit_layout_button_remove);
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.delete(
                        notesEntity
                );
                notesEntity=null;
                title.setText("");
                content.setText("");
                MainActivity.getMyFragmentManager().popBackStackImmediate();
            }
        });
        setContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        setContent();
    }
}
