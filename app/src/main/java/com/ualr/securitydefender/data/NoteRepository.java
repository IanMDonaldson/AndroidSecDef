package com.ualr.securitydefender.data;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private static Database database;
    private NoteDAO noteDAO;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteRepository(Application application) {
        this.database = Database.getDatabase(application);
        this.noteDAO = database.noteDAO();
        this.allNotes = noteDAO.getAllNotes();
    }

    public void addNote(NoteEntity noteEntity){
        new InsertNoteAsyncTask(noteDAO).execute(noteEntity);
    }
    public void updateNote(NoteEntity noteEntity){
        new UpdateNoteAsyncTask(noteDAO).execute(noteEntity);
    }
    public void deleteNote(NoteEntity noteEntity){
        new DeleteNoteAsyncTask(noteDAO).execute(noteEntity);
    }
    public LiveData<List<NoteEntity>> getAllNotes(){
        return this.noteDAO.getAllNotes();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDAO.addNote(noteEntities[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDAO.addNote(noteEntities[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDAO.addNote(noteEntities[0]);
            return null;
        }
    }
}
