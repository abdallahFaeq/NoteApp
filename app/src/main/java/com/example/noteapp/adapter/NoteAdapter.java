package com.example.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> noteList;

    public NoteAdapter(List<Note> noteList){
        this.noteList = noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.mTitle.setText(noteList.get(position).getTitle());
        holder.mContent.setText(noteList.get(position).getContent());

        if (onNoteClickListener != null){
            holder.itemView.setOnClickListener((view -> {
                onNoteClickListener.onNoteClick(position);
            }));
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder{
        TextView mTitle, mContent;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txt_title);
            mContent = itemView.findViewById(R.id.txt_content);
        }
    }
    OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public interface OnNoteClickListener{
        void onNoteClick(int position);
    }
    public Note getNote(int position){
        return noteList.get(position);
    }
}
