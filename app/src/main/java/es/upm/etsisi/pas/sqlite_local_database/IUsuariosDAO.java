package es.upm.etsisi.pas.sqlite_local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IUsuariosDAO {
    @Query("SELECT * FROM " + es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity.TABLA)
    LiveData<List<es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity grupo);

    @Query("DELETE FROM " + es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity.TABLA)
    void deleteAll();

    @Delete
    void delete(es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity grupo);
}
