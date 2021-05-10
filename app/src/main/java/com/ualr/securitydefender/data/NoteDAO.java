package com.ualr.securitydefender.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * from note")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * from note WHERE id = :id")
    LiveData<NoteEntity> getNote(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(NoteEntity noteEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteEntity noteEntity);

    @Query("DELETE FROM note")
    void deleteAll();

    @Delete
    void deleteNote(NoteEntity noteEntity);
}