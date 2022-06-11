package es.upm.etsisi.pas.recopilacion_datos;

import androidx.room.PrimaryKey;

import java.util.ArrayList;
import com.google.gson.Gson;

public class ContactEntity {
    static Gson gson = new Gson();
    protected String name;
    protected ArrayList phone_number;

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    public ContactEntity(String name) {
        this.name = name;
    }

    public ContactEntity(String name, ArrayList phone_number) {
        this(name);
        this.phone_number = phone_number;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getPhone_number() {
        return this.phone_number;
    }

    public void setPhone_number(ArrayList phone_number) {
        this.phone_number = phone_number;
    }

    public String serializeGSon(){
        return gson.toJson(this);
    }

}
