package com.ualr.securitydefender.ui.notes;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ualr.securitydefender.data.NoteEntity;
import com.ualr.securitydefender.data.NoteRepository;
import com.ualr.securitydefender.data.PasswordRepository;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private NoteRepository noteRepository;

    private static final int NOT_SELECTED = -1;
    private int noteIndex = NOT_SELECTED;

//    private MediatorLiveData<List<NoteEntity>> notes = null;

    private LiveData<List<NoteEntity>> noteEntityList;

    private MutableLiveData<Integer> selectedIndex = new MutableLiveData<>(NOT_SELECTED);
    //never called
    public NotesViewModel(@NonNull Application application) {
        super();
    }

    public NotesViewModel() {

    }
    public void setNoteRepository(Application application) {
        this.noteRepository = new NoteRepository(application);
        this.noteEntityList = noteRepository.getAllNotes();
    }
    public LiveData<Integer> getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selected) {
        this.selectedIndex.setValue(selected);
    }

    public LiveData<List<NoteEntity>> getNotes() {
        return this.noteEntityList;
    }

    public void setNotes(List<NoteEntity> noteList) {
        this.noteEntityList.getValue().addAll(noteList);
    }

    public void selectNoteAtPos(int pos) {
        List<NoteEntity> noteList = noteEntityList.getValue();
        if(noteIndex != NOT_SELECTED) {
            noteList.get(noteIndex).setSelected(false);
        }
        noteList.get(pos).toggleSelection();
        noteEntityList.getValue().addAll(noteList);
    }
    public void insert(NoteEntity noteEntity) {
        noteRepository.addNote(noteEntity);
    }
    public void update(NoteEntity noteEntity) {
        noteRepository.updateNote(noteEntity);
    }
    public void delete(NoteEntity noteEntity) {
        noteRepository.deleteNote(noteEntity);
    }
}