package com.app.development.ausho.taskmanager.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.development.ausho.taskmanager.Model.RecycleBinEntity;
import com.app.development.ausho.taskmanager.R;
import com.app.development.ausho.taskmanager.ViewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecycleBin extends AppCompatActivity {

    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    private List<RecycleBinEntity> allRecycledNotes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bin);

        Toolbar toolbar = findViewById(R.id.recycleBinToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycleBin_tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final RecycleBinAdapter recycleBinAdapter = new RecycleBinAdapter(this);
        recyclerView.setAdapter(recycleBinAdapter);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getAllRecycledNotes().observe(this, new Observer<List<RecycleBinEntity>>() {
            @Override
            public void onChanged(List<RecycleBinEntity> recycleBinEntities) {
                recycleBinAdapter.setNotes(recycleBinEntities);
                allRecycledNotes = recycleBinEntities;
            }
        });

        findViewById(R.id.recycleBin_deleteAllTasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RecycleBin.this)
                        .setTitle("Clear Recycle Bin?")
                        .setMessage("Recycle bin will be cleared permanently. You will not be able to restore the tasks later")
                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notesViewModel.clearRecycleBin();
                                recycleBinAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setCancelable(true)
                        .show();

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                RecycleBinEntity recycleBinEntity = recycleBinAdapter.getTaskAt(position);
                notesViewModel.deleteFromRecycleBin(recycleBinEntity);
                recycleBinAdapter.notifyDataSetChanged();
                Toast.makeText(RecycleBin.this, "Task deleted permanently", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(RecycleBin.this,R.color.Red))
                        .addSwipeLeftActionIcon(R.drawable.delete)
                        .addSwipeLeftLabel("Delete")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
    }
}