package telegram.free.roomlibrary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.w3c.dom.Node;

@Database(entities =Note.class,exportSchema = true,version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public static final String DB_NAME="personal_db";

    public static PersonDatabase instance;

    public static synchronized PersonDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DB_NAME).
                    fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract NoteData getNote();
}
