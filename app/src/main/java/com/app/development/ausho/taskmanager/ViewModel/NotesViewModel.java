package com.app.development.ausho.taskmanager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.development.ausho.taskmanager.Model.NotesEntity;
import com.app.development.ausho.taskmanager.Model.NotesRepository;
import com.app.development.ausho.taskmanager.Model.RecycleBinEntity;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository repository;
    private LiveData<List<NotesEntity>> allNotes;
    private LiveData<List<RecycleBinEntity>>allRecycledNotes;


    public NotesViewModel(@NonNull Application application) {
        super(application);

        //Fresh Database reference
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();

        //RecycleBin Reference
        allRecycledNotes = repository.getAllRecycledTasks();
    }


    public void insert(NotesEntity note) {
        repository.insert(note);
    }
    public void delete(NotesEntity notesEntity) {
        repository.delete(notesEntity);
    }
    public void updateNote(NotesEntity notesEntity){
        repository.updateNote(notesEntity);
    }
    public LiveData<List<NotesEntity>> getAllNotes() {
        return allNotes;
    }

    public void insertToRecycleBin(RecycleBinEntity recycleBinEntity){
        repository.insertToRecycleBin(recycleBinEntity);
    }
    public void deleteFromRecycleBin(RecycleBinEntity recycleBinEntity){
        repository.DeleteFromRecycleBin(recycleBinEntity);
    }
    public void clearRecycleBin(){
        repository.clearRecycleBin();
    }
    public LiveData<List<RecycleBinEntity>>getAllRecycledNotes(){
        return allRecycledNotes;
    }
}
