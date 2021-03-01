package com.app.development.ausho.taskmanager.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<NotesEntity>> allNotes;

    private RecycleBinDao recycleBinDao;
    private LiveData<List<RecycleBinEntity>> allRecycledTasks;

    public NotesRepository(Application application)
    {
        //Fresh Notes
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();
        allNotes=notesDao.getAllNotes();

        //Recycled Task
        RecycleBinDatabase recycleBinDatabase = RecycleBinDatabase.getInstance(application);
        recycleBinDao = recycleBinDatabase.RecycledNotesDao();
        allRecycledTasks = recycleBinDao.getAllRecycledTask();
    }

    //now giving body to all functions for various work
    public void insert(NotesEntity notes)
    {
        new InsertNotesAsyncTas(notesDao).execute(notes);
    }
    public  void delete(NotesEntity notesEntity){
        new DeleteNotesAsyncTask(notesDao).execute(notesEntity);
    }
    public void updateNote(NotesEntity notesEntity){
        new UpdateNotesAsyncTask(notesDao).execute(notesEntity);
    }
    public LiveData<List<NotesEntity>>getAllNotes()
    {
        return allNotes;
    }

    //Recycle bin reference
    public LiveData<List<RecycleBinEntity>>getAllRecycledTasks(){
        return allRecycledTasks;
    }
    public void insertToRecycleBin(RecycleBinEntity recycleBinEntity){
        new InsertNotesToRecycleBinAsyncTask(recycleBinDao).execute(recycleBinEntity);
    }
    public void DeleteFromRecycleBin(RecycleBinEntity recycleBinEntity){
        new DeleteTaskFromRecycleBinAsyncTask(recycleBinDao).execute(recycleBinEntity);
    }
    public void clearRecycleBin(){
        new ClearRecycleBinAsyncTask(recycleBinDao).execute();
    }


    private static  class InsertNotesAsyncTas extends AsyncTask<NotesEntity, Void,Void> {
        private NotesDao notesDao;
        private InsertNotesAsyncTas(NotesDao noteDAO) {
            this.notesDao = noteDAO;
        }
        @Override
        protected Void doInBackground(NotesEntity... notes) {
            notesDao.AddNotes(notes[0]); //for inserting
            return null;
        }
    }
    private static  class DeleteNotesAsyncTask extends  AsyncTask<NotesEntity,Void,Void>{
        private NotesDao notesDao;

        private DeleteNotesAsyncTask(NotesDao notesDao)
        {
            this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            notesDao.deleteNote(notesEntities[0]); //for deleting
            return null;
        }
    }
    private static  class UpdateNotesAsyncTask extends  AsyncTask<NotesEntity,Void,Void>{
        private NotesDao notesDao;
        private UpdateNotesAsyncTask(NotesDao notesDao)
        {
            this.notesDao = notesDao;
        }
        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            notesDao.updateNote(notesEntities[0]); //for updating
            return null;
        }
    }

    private static class InsertNotesToRecycleBinAsyncTask extends  AsyncTask<RecycleBinEntity,Void,Void>{
        private RecycleBinDao recycleBinDao;
        public InsertNotesToRecycleBinAsyncTask(RecycleBinDao recycleBinDao){
            this.recycleBinDao = recycleBinDao;
        }
        @Override
        protected Void doInBackground(RecycleBinEntity... recycleBinEntities) {
            recycleBinDao.addTaskToRecycleBin(recycleBinEntities[0]);
            return null;
        }
    }
    private static class DeleteTaskFromRecycleBinAsyncTask extends AsyncTask<RecycleBinEntity,Void,Void>{
        private RecycleBinDao recycleBinDao;
        public DeleteTaskFromRecycleBinAsyncTask(RecycleBinDao recycleBinDao) {
            this.recycleBinDao = recycleBinDao;
        }
        @Override
        protected Void doInBackground(RecycleBinEntity... recycleBinEntities) {
            recycleBinDao.deleteTaskFromRecycleBin(recycleBinEntities[0]);
            return null;
        }
    }
    private static class ClearRecycleBinAsyncTask extends AsyncTask<Void,Void,Void>{
        private RecycleBinDao recycleBinDao;
        public ClearRecycleBinAsyncTask(RecycleBinDao recycleBinDao){
            this.recycleBinDao = recycleBinDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            recycleBinDao.ClearRecycleBin();
            return null;
        }
    }
}
