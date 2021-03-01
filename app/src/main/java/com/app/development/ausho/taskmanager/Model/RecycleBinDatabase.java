package com.app.development.ausho.taskmanager.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = RecycleBinEntity.class,version = 2)
public abstract class RecycleBinDatabase extends  RoomDatabase{

    private static RecycleBinDatabase instance;
    public abstract RecycleBinDao RecycledNotesDao();

    public static synchronized  RecycleBinDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecycleBinDatabase.class,"RecycleBin_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
