package es.upm.etsisi.pas.roomdb_local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/* Esta clase simplemente simplifica el manejo de IUsuariosDAO
 * evitando la necesidad de hacer el tratamiento de ellos.
 * No es simplificable por motivos de construcciones cíclicas
 * que surgen debido a los automatismos transparentes que
 * simplifican la codificación del roomdb.
 */
public class UsuariosRepository {
    private IUsuariosDAO iItemDAO;
    private LiveData<List<es.upm.etsisi.pas.roomdb_local.UsuariosEntity>> ldList;

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

    public LiveData<List<es.upm.etsisi.pas.roomdb_local.UsuariosEntity>> getAll() {
        return ldList;
    }

    public long insert(es.upm.etsisi.pas.roomdb_local.UsuariosEntity item) {
        return iItemDAO.insert(item);
    }

    public void deleteAll() {
        iItemDAO.deleteAll();
    }

    public void delete(es.upm.etsisi.pas.roomdb_local.UsuariosEntity item)  {
        iItemDAO.delete(item);
    }
}
