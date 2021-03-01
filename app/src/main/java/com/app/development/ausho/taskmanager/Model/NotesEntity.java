package com.app.development.ausho.taskmanager.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotesEntity {
    private String title;
    private String description;
    private String date;
    private String start_time;
    private boolean isFinished;
    private boolean isImportant;

    @PrimaryKey(autoGenerate = true)
    private int ID;

    public NotesEntity(String title, String description, String date, String start_time, boolean isFinished, boolean isImportant) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.isFinished = isFinished;
        this.isImportant = isImportant;
    }

    public NotesEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
