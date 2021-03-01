package com.app.development.ausho.taskmanager.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecycleBinDao {

    @Insert
    void addTaskToRecycleBin(RecycleBinEntity recycleBinEntity);

    @Delete
    void deleteTaskFromRecycleBin(RecycleBinEntity recycleBinEntity);

    @Query("SELECT * FROM RecycleBinEntity")
    LiveData<List<RecycleBinEntity>>getAllRecycledTask();

    @Query("DELETE FROM RecycleBinEntity")
    void ClearRecycleBin();

}
