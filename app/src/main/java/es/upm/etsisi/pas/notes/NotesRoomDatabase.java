package es.upm.etsisi.pas.notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {NotesEntity.class}, version = 1,
        exportSchema = false)
public abstract class NotesRoomDatabase extends RoomDatabase {

    /* STATICS */
    public static final String BASE_DATOS = NotesEntity.TABLA + ".db";
    private static volatile NotesRoomDatabase INSTANCE;
    private static volatile Boolean UnderConstruction = false;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /* INSTANCES */
    public abstract INotesDAO grupoDAO();

    public static NotesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesRoomDatabase.class) {
                if (INSTANCE == null && !UnderConstruction) {
                    UnderConstruction = true;
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesRoomDatabase.class, BASE_DATOS)
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Delete all content and repopulate the database whenever the app is started
    private static Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    // If you want to keep data through app restarts,
                    // comment out the following block
                    databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Populate the database in the background.
                            // If you want to start with more groups, just add them.
                            INotesDAO dao = INSTANCE.grupoDAO();
                        }
                    });
                }
            };

}
