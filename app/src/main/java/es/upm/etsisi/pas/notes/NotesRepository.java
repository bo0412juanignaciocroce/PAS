package es.upm.etsisi.pas.notes;

import androidx.lifecycle.LiveData;

import java.util.List;

/* Esta clase simplemente simplifica el manejo de INotesDAO
 * evitando la necesidad de hacer el tratamiento de ellos.
 * No es simplificable por motivos de construcciones cíclicas
 * que surgen debido a los automatismos transparentes que
 * simplifican la codificación del roomdb.
 */
public class NotesRepository {
    private INotesDAO iItemDAO;
    private LiveData<List<NotesEntity>> ldList;

    /**
     * Constructor
     *
     * @param application app
     */
    public NotesRepository(NotesRoomDatabase db) {
        iItemDAO = db.grupoDAO();
        ldList = iItemDAO.getAll();
    }

    public LiveData<List<NotesEntity>> getAll() {
        return ldList;
    }

    public long insert(NotesEntity item) {
        return iItemDAO.insert(item);
    }

    public void deleteAll() {
        iItemDAO.deleteAll();
    }

    public void delete(NotesEntity item)  {
        iItemDAO.delete(item);
    }
}
