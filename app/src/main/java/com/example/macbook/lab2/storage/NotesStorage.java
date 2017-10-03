package com.example.macbook.lab2.storage;

import com.example.macbook.lab2.models.Note;

import java.util.ArrayList;

public class NotesStorage {
    private static ArrayList<Note> notes = new ArrayList<>();

    public static ArrayList<Note> get() {
        return notes;
    }

    public static Note get(int position) {
        return notes.get(position);
    }

    public static Note getById(int id) {
        for (Note note: notes){
            if (note.getId() == id){
                return note;
            }
        }

        return null;
    }

    public static void add(Note note) {
        notes.add(note);
    }

    public static void remove(int position) {
        notes.remove(position);
    }

    public static void remove(Note note) {
        notes.remove(note);
    }

    public static ArrayList<Note> find(String s) {
        ArrayList<Note> result = new ArrayList<>();
        for (Note note: notes) {
            if (note.getTitle().toLowerCase().contains(s.toLowerCase()) ||
                note.getDescription().toLowerCase().contains(s.toLowerCase())) {
                result.add(note);
            }
        }

        return result;
    }

    public static ArrayList<Note> getHigh() {
        return findByImportance(2);

    }

    public static ArrayList<Note> getLow() {
        return findByImportance(1);
    }

    public static ArrayList<Note> getDefault() {
        return findByImportance(0);
    }

    private static ArrayList<Note> findByImportance(int importance){
        ArrayList<Note> result = new ArrayList<>();
        for (Note note: notes) {
            if (note.getImportance() == importance) {
                result.add(note);
            }
        }

        return result;
    }
}
