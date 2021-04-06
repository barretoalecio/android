package br.ucsal.contacts.utils;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.ucsal.contacts.models.Contact;
import br.ucsal.contacts.repository.ContactRepository;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    public abstract ContactRepository repository();

    public static final int NUMBER_OF_THREADS = 4;

    private static volatile ContactRoomDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ContactRoomDatabase getDatabase(final Context context) {

        if(context != null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDatabase.class, "contact_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }


    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        ContactRepository repository = INSTANCE.repository();
//                        repository.deleteAll();
                        Contact contact = new Contact("Alécio Barreto", "(71) 99275-2384", 20);
                        Contact contact2 = new Contact("Ester Mabel", "(71) 99275-2384", 20);
                        Contact contact3 = new Contact("Yllo Luis", "(71) 99275-2384", 22);
                        Contact contact4 = new Contact("Michel Marques", "(71) 99275-2384", 19);
                        Contact contact5 = new Contact("Ary Sault", "(71) 99275-2384", 19);
                        repository.insert(contact);
                        repository.insert(contact2);
                        repository.insert(contact3);
                        repository.insert(contact4);
                        repository.insert(contact5);
                    });
                }
            };
}