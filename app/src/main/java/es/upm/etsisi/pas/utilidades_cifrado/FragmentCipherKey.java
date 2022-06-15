package es.upm.etsisi.pas.utilidades_cifrado;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import es.upm.etsisi.pas.MainActivity;
import es.upm.etsisi.pas.R;

public class FragmentCipherKey extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.cipher_key_layout, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        ImageButton b = view.findViewById(R.id.cipher_key_layout_button_add);
        TextView tv = view.findViewById(R.id.cipher_key_layout_cipher_key);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv.getText().length()>0){
                    SharedPreferences.Editor editor = MainActivity.getSharedPreferences().edit();
                    String texto = tv.getText().toString();
                    editor.putString(getString(R.string.cipherKey), texto);
                    editor.apply();
                    MainActivity.updateCipherKey();
                    getActivity().onBackPressed();
                }
            }
        });
    }
}
