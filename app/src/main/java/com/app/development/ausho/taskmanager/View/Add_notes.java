package com.app.development.ausho.taskmanager.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import com.app.development.ausho.taskmanager.Model.DatePickerFragment;
import com.app.development.ausho.taskmanager.Model.NotesEntity;
import com.app.development.ausho.taskmanager.Model.StartTimePickerFragment;
import com.app.development.ausho.taskmanager.R;
import com.app.development.ausho.taskmanager.ViewModel.NotesViewModel;
import java.text.DateFormat;
import java.util.Calendar;

public class Add_notes extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private String start_time = "";
    private String date = "";
    EditText title, body;
    RadioButton isImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        title = findViewById(R.id.add_notes_title);
        body = findViewById(R.id.add_notes_body);
        isImp = findViewById(R.id.add_notes_isImp);

        findViewById(R.id.add_notes_save_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String Title = title.getText().toString();
        String Body = body.getText().toString();
        boolean IsImp = isImp.isChecked();
        if(!Title.equalsIgnoreCase(""))
        {
            NotesEntity notesEntity = new NotesEntity(Title,Body,date,start_time,false,IsImp);
            NotesViewModel notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
            notesViewModel.insert(notesEntity);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Add_notes.this,NotesListPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Enter the Title", Toast.LENGTH_SHORT).show();
        }

    }

    //Pickers
    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        date = DateFormat.getDateInstance().format(c.getTime());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR,hour);
        c.set(Calendar.MINUTE,minute);
        start_time = DateFormat.getTimeInstance().format(c.getTime());
    }
}