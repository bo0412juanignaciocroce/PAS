package es.upm.etsisi.pas.recopilacion_datos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import es.upm.etsisi.pas.DebugTags;

public class RetrieveContacts extends AppCompatActivity  {
    public static final int REQUEST_READ_CONTACTS = 79;
    ArrayList<ContactEntity> contactList;

    public RetrieveContacts(Context context, Activity activity, ContentResolver cr) {
        contactList = getAllContacts(cr);
        FirebaseContacts firebasecontacts = new FirebaseContacts();
        firebasecontacts.UploadContacts(contactList, context);
    }


    @SuppressLint("Range")
    private ArrayList<ContactEntity> getAllContacts(ContentResolver cr) {
        ArrayList<ContactEntity> contactList = new ArrayList<>();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if(cur == null)
            return contactList;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    ArrayList<String> phoneList = new ArrayList<>();
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneList.add(phoneNo);
                    }
                    pCur.close();
                    contactList.add(new ContactEntity(name, phoneList));
                }
            }
        }
        cur.close();
        return contactList;
    }
}
