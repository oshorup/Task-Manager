package com.app.development.ausho.taskmanager.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.development.ausho.taskmanager.Model.RecycleBinEntity;
import com.app.development.ausho.taskmanager.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleBinAdapter extends RecyclerView.Adapter<RecycleBinAdapter.RecycleBinHolder> {
    private List<RecycleBinEntity> AllRecycledNotes = new ArrayList<RecycleBinEntity>();
    Context context;

    public RecycleBinAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleBinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_task_design, parent, false);
        return new RecycleBinHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleBinHolder holder, int position) {

        RecycleBinEntity recycleBinEntity = AllRecycledNotes.get(position);
        holder.title.setText(recycleBinEntity.getTitle());
        holder.body.setText(recycleBinEntity.getDescription());

        holder.date.setText(recycleBinEntity.getDate());
        holder.start_time.setText(recycleBinEntity.getStart_time());

        holder.isImportant.setVisibility(View.VISIBLE);
        if (recycleBinEntity.isFinished()) {
            holder.isImportant.setImageDrawable(context.getResources().getDrawable(R.drawable.assignment_turned_in));
        } else {
            holder.isImportant.setImageDrawable(context.getResources().getDrawable(R.drawable.assignment_due));
        }
    }

    @Override
    public int getItemCount() {
        return AllRecycledNotes.size();
    }

    public void setNotes(List<RecycleBinEntity> recycleBinEntities) {
        this.AllRecycledNotes = recycleBinEntities;
        notifyDataSetChanged();
    }

    public RecycleBinEntity getTaskAt(int position) {
        return AllRecycledNotes.get(position);
    }


    public class RecycleBinHolder extends RecyclerView.ViewHolder {
        TextView title, body, date, start_time;
        ImageView isImportant;

        public RecycleBinHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.task_title);
            body = itemView.findViewById(R.id.short_description);
            date = itemView.findViewById(R.id.date);
            start_time = itemView.findViewById(R.id.time);
            isImportant = itemView.findViewById(R.id.high_priority);

        }
    }
}
