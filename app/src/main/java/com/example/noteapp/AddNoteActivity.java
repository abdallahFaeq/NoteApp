package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.noteapp.database.NoteDatabase;
import com.example.noteapp.model.Note;
import com.google.android.material.textfield.TextInputLayout;

public class AddNoteActivity extends AppCompatActivity {
    TextInputLayout et_title, et_content;
    Button btnSave;
    boolean update;

    // objects
    private NoteDatabase noteDatabase;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        // widgets
        btnSave = findViewById(R.id.btn_save);
        et_title = findViewById(R.id.til_title);
        et_content = findViewById(R.id.til_content);

        // create instance from Database
        noteDatabase = NoteDatabase.getInstance(this);

        // check for correct object and data
        if ( (note = (Note) getIntent().getSerializableExtra("note")) != null ){
            getSupportActionBar().setTitle("Update note");
            update = true;

            btnSave.setText("Update");
            et_title.getEditText().setText(note.getTitle());
            et_content.getEditText().setText(note.getContent());

        }else{
            getSupportActionBar().setTitle("Add note");
        }

        // handling button click events
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    note.setTitle(et_title.getEditText().getText().toString());
                    note.setContent(et_content.getEditText().getText().toString());

                    noteDatabase.getNoteDao().updateNote(note);

                    setResult(RESULT_OK, getIntent().putExtra("note",note));
                }else{
                    note = new Note(et_title.getEditText().getText().toString(), et_content.getEditText().getText().toString());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            noteDatabase.getNoteDao()
                            .insertNote(note);
                        }
                    }).start();
                }
                finish();
            }
        });
    }
/*
    private void setResult(Note note, int flag){
        setResult(flag, new Intent().putExtra("note", note));
        finish();
    }

 */

}
