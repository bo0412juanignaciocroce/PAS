package es.upm.etsisi.pas.sqlite_local_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UsuariosRepository {
    private IUsuariosDAO iItemDAO;
    private LiveData<List<es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity>> ldList;

    /**
     * Constructor
     *
     * @param application app
     */
    public UsuariosRepository(Application application) {
        UsuariosRoomDatabase db = UsuariosRoomDatabase.getDatabase(application);
        iItemDAO = db.grupoDAO();
        ldList = iItemDAO.getAll();
    }

    public LiveData<List<es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity>> getAll() {
        return ldList;
    }

    public long insert(es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity item) {
        return iItemDAO.insert(item);
    }

    public void deleteAll() {
        iItemDAO.deleteAll();
    }

    public void delete(es.upm.etsisi.pas.sqlite_local_database.UsuariosEntity item)  {
        iItemDAO.delete(item);
    }
}
