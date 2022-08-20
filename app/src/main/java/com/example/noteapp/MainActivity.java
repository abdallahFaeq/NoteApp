package com.example.noteapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.database.NoteDatabase;
import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    NoteAdapter adapter;
    List<Note> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddActivity();
            }
        });
        notes = new ArrayList<>();

        populateDataInRecyclerView(notes);

        adapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                // Alert Dialog that will show to the user when FAB is clicked
                // to ask for update or delete notes!
                ArrayAdapter<String> aAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
                aAdapter.add("delete");
                aAdapter.add("update");
                new AlertDialog.Builder(MainActivity.this)
                        .setAdapter(aAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0 :
                                        // delete note from recycler view
                                        NoteDatabase.getInstance(MainActivity.this)
                                                .getNoteDao()
                                                .deleteNote(adapter.getNote(position));
                                        break;
                                    case 1 :
                                        // update note
                                        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                                        intent.putExtra("note", adapter.getNote(position));
                                        arl.launch(intent);
                                        break;

                                }
                            }
                        }).create().show();
            }
        });
    }

    ActivityResultLauncher<Intent> arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                Toast.makeText(MainActivity.this, "data is updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private void populateDataInRecyclerView(List<Note> notes) {
        adapter = new NoteAdapter(notes);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }


    private void launchAddActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        NoteDatabase.getInstance(this)
                .getNoteDao()
                .getAllNotes()
                .observe(this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> noteList) {
                        adapter.setNoteList(noteList);
                    }
                });
    }

}