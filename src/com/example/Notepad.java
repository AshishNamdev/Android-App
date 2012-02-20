package com.example;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class Notepad extends ListActivity {
    public static final int INSERT_ID = Menu.FIRST;

    private int noteNumber;
    private NotesDbAdapter notesDbAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_list);
        notesDbAdapter = new NotesDbAdapter(this);
        notesDbAdapter.open();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        String noteName = "Note " + noteNumber++;
        notesDbAdapter.createNote(noteName, "");
        fillData();
    }

    private void fillData() {
        Cursor cursor = notesDbAdapter.fetchAllNotes();
        startManagingCursor(cursor);

        String[] from = {NotesDbAdapter.KEY_TITLE};
        int[] to = {R.id.text1};


        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, cursor, from, to);
        setListAdapter(notes);
    }
}
