package com.example.caz.recreated_notetaker;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class NoteRepository {

//    private ArrayList<Note> notes;            //instructor commented this out because we are no longer storing in arraylist, storing in SharedPrefsDao instead

//    public NoteRepository() {
//        this.notes = new ArrayList<>();
//    }



    // but we still need to create an arraylist to pass to viewmodel

    public MutableLiveData<ArrayList<Note>> getNotes() {
        MutableLiveData<ArrayList<Note>> liveDataList = new MutableLiveData<>();
        liveDataList.setValue(SharedPrefsDao.getAllNotes());
        return liveDataList;
    }

    public ArrayList<Note> addNote(Note note) {
        SharedPrefsDao.setNote(note);
        return SharedPrefsDao.getAllNotes();
    }
}
