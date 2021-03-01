package com.app.development.ausho.taskmanager.View;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.development.ausho.taskmanager.Model.NotesEntity;
import com.app.development.ausho.taskmanager.Model.RecycleBinEntity;
import com.app.development.ausho.taskmanager.R;
import com.app.development.ausho.taskmanager.ViewModel.NotesViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class NotesListPage extends AppCompatActivity {

    private NotesViewModel notesViewModel;
    private List<NotesEntity> allNotes = new ArrayList<>();
    RecyclerView recyclerView;
    private List<RecycleBinEntity>allRecycledNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list_page);

        //loading ads
        AdView mAdView = findViewById(R.id.adView_notesList);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //set toolbar as actionbar
        Toolbar toolbar = findViewById(R.id.notesListToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NotesAdapter noteAdapter = new NotesAdapter(this);
        recyclerView.setAdapter(noteAdapter);

        final RecycleBinAdapter recycleBinAdapter = new RecycleBinAdapter(this);

        //getting instance of ViewModel
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //now observing the LiveData of ViewModel
        notesViewModel.getAllNotes().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(List<NotesEntity> notes) {
                //now update the recyclerView
                noteAdapter.setNotes(notes);

                allNotes = notes;
                //make overview
                makeOverview(noteAdapter,recycleBinAdapter);
            }
        });

        findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesListPage.this, Add_notes.class);
                startActivity(intent);
            }
        });

        //Swiping feature
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final NotesEntity notesEntity = noteAdapter.getNotesAt(viewHolder.getAdapterPosition());
                notesViewModel.delete(notesEntity);
                noteAdapter.notifyDataSetChanged();

                final RecycleBinEntity recycleBinEntity = new RecycleBinEntity(notesEntity.getTitle(),notesEntity.getDescription(),
                        notesEntity.getDate(),notesEntity.getStart_time(),true,notesEntity.isImportant());

                notesViewModel.insertToRecycleBin(recycleBinEntity);
                recycleBinAdapter.notifyDataSetChanged();

                Snackbar.make(recyclerView,"Task finished and Moved to recycle bin",Snackbar.LENGTH_SHORT)
                        .show();

                //TODO - In future give the option for undoing the finish status of task
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(NotesListPage.this,R.color.ForestGreen))
                        .addSwipeLeftActionIcon(R.drawable.assignment_turned_in)
                        .addSwipeLeftLabel("Finished")
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        notesViewModel.getAllRecycledNotes().observe(this, new Observer<List<RecycleBinEntity>>() {
            @Override
            public void onChanged(List<RecycleBinEntity> recycleBinEntities) {

                recycleBinAdapter.setNotes(recycleBinEntities);
                allRecycledNotes = recycleBinEntities;
                makeOverview(noteAdapter,recycleBinAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "I am About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.recycleBin:
                Intent intent = new Intent(NotesListPage.this,RecycleBin.class);
                startActivity(intent);
                return true;
            case R.id.privacy_policy:
                Toast.makeText(this, "I am Privacy Policy", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeOverview(NotesAdapter notesAdapter,RecycleBinAdapter recycleBinAdapter) {
        TextView totalTask = findViewById(R.id.totalTask);
        TextView taskFinished = findViewById(R.id.taskFinished);
        TextView taskImportant = findViewById(R.id.taskCrucial);

        totalTask.setText(String.valueOf(notesAdapter.getItemCount()));

        //temporary function for accessing important, progressing and finished task, later I will achieve it with database
        int countImportant = 0;
        for (NotesEntity notesEntity : allNotes) {
            if (notesEntity.isImportant()) countImportant++;
        }
        taskFinished.setText(String.valueOf(recycleBinAdapter.getItemCount()));
        taskImportant.setText(String.valueOf(countImportant));
    }

}