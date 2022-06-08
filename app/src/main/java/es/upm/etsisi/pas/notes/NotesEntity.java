package es.upm.etsisi.pas.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = NotesEntity.TABLA)
public class NotesEntity {
    static public final String TABLA = "notes";

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    protected String nombre;

    protected String password;

    protected float rol;

    public NotesEntity(String nombre, String password, float rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public int getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public float getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(float rol) {
        this.rol = rol;
    }
}
