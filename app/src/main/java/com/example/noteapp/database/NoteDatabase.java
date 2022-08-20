package com.example.noteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.noteapp.converter.DataRoomConverter;
import com.example.noteapp.dao.NoteDao;
import com.example.noteapp.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)

@TypeConverters({DataRoomConverter.class})

public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();

    private static NoteDatabase instance;
    // singleton pattern
    public static NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, NoteDatabase.class, "notes_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
