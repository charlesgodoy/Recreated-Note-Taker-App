package com.example.caz.recreated_notetaker;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class NoteViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Note>> noteList;
    private NoteRepository repo;

    public LiveData<ArrayList<Note>> getNotesList() {
        if(noteList == null) {      // null meaning if we haven't given it a value yet
            loadList();
        }
        return noteList;
    }


    // private so no one can create it and void cause value is stored here, not returned
    // also, can only get here is noteList is null
    private void loadList() {
        repo = new NoteRepository();
        noteList = repo.getNotes();
    }

    //method that accepts a note
    public void addNote(Note note) {
        if(noteList != null) {
            noteList.setValue(repo.addNote(note));
        }
    }
}
