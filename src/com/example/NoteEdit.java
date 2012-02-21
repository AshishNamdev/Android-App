package com.example;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteEdit extends Activity {
    private EditText titleField;
    private EditText bodyField;
    private Long rowId;

    private NotesDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new NotesDbAdapter(this);
        dbAdapter.open();

        setContentView(R.layout.note_edit);

        setTitle(R.string.edit_note);
        titleField = (EditText) findViewById(R.id.title);
        bodyField = (EditText) findViewById(R.id.body);
        Button confirm = (Button) findViewById(R.id.confirm);

        rowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
        if (rowId == null) {
            Bundle extras = getIntent().getExtras();
            rowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID) : null;
        }
        populateFields();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_ROWID, rowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String title = titleField.getText().toString();
        String body = bodyField.getText().toString();

        if (rowId == null) {
            long id = dbAdapter.createNote(title, body);
            if (id > 0) {
                rowId = id;
            }
        } else {
            dbAdapter.updateNote(rowId, title, body);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }


    private void populateFields() {
        if (rowId != null) {
            Cursor note = dbAdapter.fetchNote(rowId);
            startManagingCursor(note);
            titleField.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
            bodyField.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        }
    }
}
