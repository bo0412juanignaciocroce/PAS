package es.upm.etsisi.pas.roomdb_local;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.upm.etsisi.pas.MainActivity;

@Database(entities = {es.upm.etsisi.pas.roomdb_local.UsuariosEntity.class}, version = 1,
        exportSchema = false)
public abstract class UsuariosRoomDatabase extends RoomDatabase {

    /* STATICS */
    public static final String BASE_DATOS = es.upm.etsisi.pas.roomdb_local.UsuariosEntity.TABLA + ".db";
    private static volatile UsuariosRoomDatabase INSTANCE;
    private static volatile Boolean UnderConstruction = false;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UsuariosRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsuariosRoomDatabase.class) {
                if (INSTANCE == null && !UnderConstruction) {
                    UnderConstruction = true;
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UsuariosRoomDatabase.class, BASE_DATOS)
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
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

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
                            IUsuariosDAO dao = INSTANCE.grupoDAO();
                        }
                    });
                }
            };

    /* INSTANCES */
    public abstract IUsuariosDAO grupoDAO();
}
