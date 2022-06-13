package es.upm.etsisi.pas.notes;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.upm.etsisi.pas.DebugTags;

/* Esta clase simplemente simplifica el manejo de INotesDAO
 * evitando la necesidad de hacer el tratamiento de ellos.
 * No es simplificable por motivos de construcciones cíclicas
 * que surgen debido a los automatismos transparentes que
 * simplifican la codificación del roomdb.
 */
public class NotesRepository {
    private final INotesDAO iItemDAO;
    private final LiveData<List<NotesEntity>> ldList;

    /**
     * Constructor
     */
    public NotesRepository(NotesRoomDatabase db) {
        iItemDAO = db.grupoDAO();
        ldList = iItemDAO.getAll();
    }

    public LiveData<List<NotesEntity>> getAll() {
        return ldList;
    }

    public void insert(NotesEntity item) {
        Log.d(DebugTags.FRAGMENT_TAG,"Size: "+ldList.getValue().size());
        iItemDAO.insert(item);
    }

    public void update(NotesEntity item){
        iItemDAO.insert(item);
    }

    public void deleteAll() {
        iItemDAO.deleteAll();
    }

    public void delete(NotesEntity item)  {
        iItemDAO.delete(item);
    }
}
