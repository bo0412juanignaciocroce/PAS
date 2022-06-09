package es.upm.etsisi.pas.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = NotesEntity.TABLA)
public class NotesEntity {
    static public final String TABLA = "notes";

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    protected String titulo;

    protected String contenido;

    public NotesEntity(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public int getUid() {
        return uid;
    }

    public String getNombre() {
        return titulo;
    }

    public String getPassword() {
        return contenido;
    }

    public void setNombre(String titulo) {
        this.titulo = titulo;
    }

    public void setPassword(String contenido) {
        this.contenido = contenido;
    }

}