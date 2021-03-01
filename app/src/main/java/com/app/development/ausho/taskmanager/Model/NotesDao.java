package com.app.development.ausho.taskmanager.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.development.ausho.taskmanager.Model.NotesEntity;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void AddNotes(NotesEntity notesEntity);

    @Query("SELECT * FROM NotesEntity")
    LiveData<List<NotesEntity>> getAllNotes();

    @Delete
    void deleteNote(NotesEntity notesEntity);

    @Update
    void updateNote(NotesEntity notesEntity);

}
