package com.example.macbook.lab2.models;

import android.net.Uri;

import java.util.Date;
import java.util.Random;

public class Note {
    private int id;
    private String title;
    private String description;
    private Uri imageUri;
    private Date date;
    private int importance;

    public int getId() {
        return id;
    }

    public Note(){
        id = (new Random()).nextInt();
        date = new Date();
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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
