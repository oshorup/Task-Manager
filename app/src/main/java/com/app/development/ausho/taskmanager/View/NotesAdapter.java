package com.app.development.ausho.taskmanager.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.development.ausho.taskmanager.Model.NotesEntity;
import com.app.development.ausho.taskmanager.R;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private List<NotesEntity> notes = new ArrayList<NotesEntity>();
    Context context;

    public NotesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_task_design, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {

        NotesEntity currentNote = notes.get(position);

        holder.title.setText(currentNote.getTitle());
        holder.body.setText(currentNote.getDescription());

        holder.date.setText(currentNote.getDate());
        holder.start_time.setText(currentNote.getStart_time());

        if (currentNote.isImportant()) {
            holder.isImportant.setVisibility(View.VISIBLE);
        } else {
            holder.isImportant.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public void setNotes(List<NotesEntity> notes1) {
        this.notes = notes1;
        notifyDataSetChanged();
    }

    public NotesEntity getNotesAt(int position) {
        return notes.get(position);
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView title, body, date, start_time;
        ImageView isImportant;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            body = itemView.findViewById(R.id.short_description);
            date = itemView.findViewById(R.id.date);
            start_time = itemView.findViewById(R.id.time);
            isImportant = itemView.findViewById(R.id.high_priority);

        }
    }

}
