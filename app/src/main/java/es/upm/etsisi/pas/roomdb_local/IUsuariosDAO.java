package es.upm.etsisi.pas.roomdb_local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IUsuariosDAO {
    @Query("SELECT * FROM " + es.upm.etsisi.pas.roomdb_local.UsuariosEntity.TABLA)
    LiveData<List<es.upm.etsisi.pas.roomdb_local.UsuariosEntity>> getAll();

    /* Insert está configurado para reescribir datos en caso de coincidir */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(es.upm.etsisi.pas.roomdb_local.UsuariosEntity grupo);

    @Query("DELETE FROM " + es.upm.etsisi.pas.roomdb_local.UsuariosEntity.TABLA)
    void deleteAll();

    @Delete
    void delete(es.upm.etsisi.pas.roomdb_local.UsuariosEntity grupo);
}
