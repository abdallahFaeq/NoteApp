package com.example.noteapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.noteapp.constants.Constant;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = Constant.TABLE_NAME)
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long Note_id;
    @ColumnInfo(name = "note_content")
    private String content;
    private String title;
    private Date date;

    public Note(String title, String content) {
        this.content = content;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());
    }
    @Ignore
    public Note(){

    }


    public long getNote_id() {
        return Note_id;
    }

    public void setNote_id(long note_id) {
        Note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Note_id == note.Note_id && Objects.equals(content, note.content) && Objects.equals(title, note.title) && Objects.equals(date, note.date);
    }

    @Override
    public String toString() {
        return "Note{" +
                "Note_id=" + Note_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
