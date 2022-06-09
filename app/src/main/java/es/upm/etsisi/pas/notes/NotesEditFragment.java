package es.upm.etsisi.pas.notes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;


public class NotesEditFragment extends Fragment {
    private NotesRepository repository;
    private NotesEntity notesEntity;

    private EditText title;
    private EditText content;
    private ImageButton b;

    public NotesEditFragment(NotesRepository repository){
        this.repository = repository;
    }

    public void setCurrentNotesEntity(NotesEntity entity){
        notesEntity = entity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.notes_edit_layout, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        title = view.findViewById(R.id.notes_edit_layout_title);
        content = view.findViewById(R.id.notes_edit_layout_content);
        b = view.findViewById(R.id.notes_edit_layout_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().length()==0){
                    return;
                }
                if(content.getText().length()==0){
                    return;
                }
                notesEntity.setTitle(title.getText().toString());
                notesEntity.setContent(content.getText().toString());
                repository.update(
                        notesEntity
                );
                notesEntity=null;
                title.setText("");
                content.setText("");
                MainActivity.getMyFragmentManager().popBackStackImmediate();
            }
        });
        /* Set data to be displayed */
        title.setText(notesEntity.getTitle());
        content.setText(notesEntity.getContent());
    }

    @Override
    public void onResume() {
        super.onResume();
        title.setText(notesEntity.getTitle());
        content.setText(notesEntity.getContent());
    }
}
