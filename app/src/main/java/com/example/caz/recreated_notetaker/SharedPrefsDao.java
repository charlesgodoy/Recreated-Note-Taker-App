package com.example.caz.recreated_notetaker;

import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPrefsDao {

    private static final String KEY_IDS = "key_ids";
    private static final String KEY_ID_PREFIX = "key_ids";
    private static final String NEXT_KEY_ID = "key_next_id";

    private static String getIdsString() {

        String keyIds = "";
        if (MainActivity.preferences != null) {
            keyIds = MainActivity.preferences.getString(KEY_IDS,"");
        }
        return keyIds;
    }

    // Get Ids here
    private static String[] getAllIds() {
        // keys are stored as CSV string 1,5,6,8,7
        String[] ids = getIdsString().split(",");
        return ids;       // remove commas
    }


    // gets all notes
    public static ArrayList<Note>getAllNotes() {
        String[] ids = getAllIds();
        ArrayList<Note> notes = new ArrayList<>(ids.length);        // Instructor: if we add ids.length, we don't need to resize arraylist as it grows
        for(String id: ids) {
            if(!id.equals("")) {
                notes.add(getNote(id));
            }
        }
        return notes;
    }


    // This gets single Note back from ID
    private static Note getNote(String id) {
        Note note = null;
            if (MainActivity.preferences != null) {
                final String noteString = MainActivity.preferences.getString(KEY_ID_PREFIX + id, "");
                note = new Note(noteString);
            }
            return note;
        }



        // This first retrives NEXT_KEY_ID for next note
        // Then increments the following id by 1 and store it in nextId
        // Finally, apply it
        private static int getNextId() {
            int currentId = 0;

            if (MainActivity.preferences != null) {
                currentId = MainActivity.preferences.getInt(NEXT_KEY_ID, 0);
                int nextId = currentId + 1;      // not increment (++) because it will store it in currentId, we want nextId to be 1 and currentId to remain 0

//            int currentId = Integer.parseInt(nextId);       //removed by Instructor

                //now to get editor
                SharedPreferences.Editor editor = MainActivity.preferences.edit();
                editor.putInt(NEXT_KEY_ID, nextId);
                editor.apply();
            }

            return currentId;
    }

    // this will either add a new note or update existing note
    public static void setNote(Note note) {
        if(note.getId() == Note.NO_ID) {        // if statement used for new Notes without id
            note.setId(getNextId());
        }

        //Now get all IDs
        String[] ids = getAllIds();
        boolean exists = false;                 // instructor creating flag
        for(String id: ids) {
            if(!id.equals("")) {
            if(note.getId() == Integer.parseInt(id)) {
                exists = true;
                break;                           // break will let us exist so we can can stop loop after we find it
            }
            }
        }

        if(!exists) {
            addId(note.getId());
        }
        addNote(note);

    }



    private static void addNote(Note note) {
        SharedPreferences.Editor editor = MainActivity.preferences.edit();
        editor.putString(KEY_ID_PREFIX + note.getId(), note.toCsvString());
        editor.apply();
    }

    // appends id to the idsString
    private static void addId(int id) {
        String idsString = getIdsString();
        idsString  = idsString + "," + id;

        // now to store ids with editor
        SharedPreferences.Editor editor = MainActivity.preferences.edit();
        editor.putString(KEY_IDS, idsString.replace(" ", ""));      // ---replace was used to fix a bug where a unknown space is being added into the code
        editor.apply();
    }
}
