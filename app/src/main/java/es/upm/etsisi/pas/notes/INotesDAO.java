package es.upm.etsisi.pas.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface INotesDAO {
    @Query("SELECT * FROM " + NotesEntity.TABLA)
    LiveData<List<NotesEntity>> getAll();

    /* Insert está configurado para reescribir datos en caso de coincidir */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NotesEntity grupo);

    @Query("UPDATE "+NotesEntity.TABLA+" SET title=:title WHERE uid=:id")
    void updateTitle(String title, int id);

    @Query("UPDATE "+NotesEntity.TABLA+" SET content=:content WHERE uid=:id")
    void updateContent(String content, int id);

    @Query("DELETE FROM " + NotesEntity.TABLA)
    void deleteAll();

    @Delete
    void delete(NotesEntity grupo);
}
