package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteEdit extends Activity {
    private EditText titleField;
    private EditText bodyField;
    private Long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);
        setTitle(R.string.edit_note);
        titleField = (EditText) findViewById(R.id.title);
        bodyField = (EditText) findViewById(R.id.body);
        Button confirm = (Button) findViewById(R.id.confirm);
        rowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(NotesDbAdapter.KEY_TITLE);
            String body = extras.getString(NotesDbAdapter.KEY_BODY);
            rowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
            if(title != null) {
                titleField.setText(title);
            }
            if(body != null) {
                bodyField.setText(body);
            }
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(NotesDbAdapter.KEY_TITLE, titleField.getText().toString());
                bundle.putString(NotesDbAdapter.KEY_BODY, bodyField.getText().toString());
                if(rowId != null) {
                    bundle.putLong(NotesDbAdapter.KEY_ROWID, rowId);
                }
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
