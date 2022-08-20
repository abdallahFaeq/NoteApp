package com.example.noteapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.constants.Constant;
import com.example.noteapp.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Delete
    void deleteAllNotes(Note... notes);
    @Update
    void updateNote(Note note);
    @Query("select * from "+ Constant.TABLE_NAME)
    LiveData<List<Note>> getAllNotes();
}
