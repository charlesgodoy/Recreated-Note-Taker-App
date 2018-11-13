package com.example.caz.recreated_notetaker;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class NoteRepository {

    private ArrayList<Note> notes;

    public NoteRepository() {
        this.notes = new ArrayList<>();
    }

    public MutableLiveData<ArrayList<Note>> getNotes() {
        MutableLiveData<ArrayList<Note>> liveDataList = new MutableLiveData<>();
        liveDataList.setValue(notes);
        return liveDataList;
    }

    public ArrayList<Note> addNote(Note note) {
        if(note.getId() == Note.NO_ID) {
            int noteIndex = notes.size();
            note.setId(noteIndex);
            notes.add(note);
        } else {
            notes.set(note.getId(), note);
        }

        return notes;
    }
}
