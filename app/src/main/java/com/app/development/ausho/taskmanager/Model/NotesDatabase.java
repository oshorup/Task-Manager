package com.app.development.ausho.taskmanager.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NotesEntity.class ,version = 2)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase instance;
    public abstract NotesDao notesDao();

    public static synchronized  NotesDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class,"note_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
