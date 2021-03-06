package com.example.caz.recreated_notetaker;

        import android.app.Activity;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Context         context;
    private LinearLayout    listLayout;
    private NoteViewModel viewModel;

    public static final int EDIT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // notes = new ArrayList<>();
        context = this;
        listLayout = findViewById(R.id.list_layout);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        final android.arch.lifecycle.Observer<ArrayList<Note>> observer = new android.arch.lifecycle.Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Note> notes) {

                if(notes != null) {
                    refreshListView(notes);
                }

            }
        };

        viewModel.getNotesList().observe(this, observer);

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                Note newNote = new Note(Note.NO_ID);

                intent.putExtra(EditActivity.EDIT_NOTE_KEY, newNote);
                startActivityForResult(intent, EDIT_REQUEST_CODE);

                /*notes.add(System.currentTimeMillis());
                int noteIndex = notes.size() - 1;
                listLayout.addView(getDefaultTextView(notes.get(noteIndex).toString()));
                Log.i(getLocalClassName(), notes.toString());*/
            }
        });
    }

    private TextView getDefaultTextView(final Note note) {
        TextView textView = new TextView(context);
        textView.setText(note.getTitle());
        textView.setTextSize(24);
        textView.setPadding(10, 10, 10, 10);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_NOTE_KEY, note);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        return textView;
    }

    private void refreshListView(ArrayList<Note> notes) {
        listLayout.removeAllViews();
        for(Note note: notes) {
            listLayout.addView(getDefaultTextView(note));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == EDIT_REQUEST_CODE) {
                if(data != null) {
                    Note returnedNote = (Note)data.getSerializableExtra(EditActivity.EDIT_NOTE_KEY);

//                    boolean foundNote = false;
//                    for(int i = 0; i < notes.size(); ++i) {
//                        if(notes.get(i).getId() == returnedNote.getId()) {
//                            // this created a bug with an infinite loop, with each loop,
//                            // an element is inserted into the beginning of the arraylist
////                            notes.add(i, returnedNote);
//                            notes.set(i, returnedNote);
//                            foundNote = true;
//                        }
//                    }
//                    if(!foundNote) {
//                        notes.add(returnedNote);
//                    }
//                    refreshListView();

                    viewModel.addNote(returnedNote);
                }
            }
        }
    }
}