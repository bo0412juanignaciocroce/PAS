package es.upm.etsisi.pas.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity(tableName = NotesEntity.TABLA)
public class NotesEntity {
    static public final String TABLA = "notes";

    @PrimaryKey(autoGenerate = true)
    protected int uid;

    protected String title;

    protected String content;

    public NotesEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String serializeGSon(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}