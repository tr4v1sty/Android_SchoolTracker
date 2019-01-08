package com.travis.c196_project;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NotesActivity extends MainActivity {

    public EditText ptNotesName;
    public EditText etNotesMultiText;
    private long courseId;
    String notesName;
    String notesBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Variables for the controls
        ptNotesName = findViewById(R.id.ptNotesName);
        etNotesMultiText = findViewById(R.id.etNotesMultiText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getLong("courseId");

            DatabaseConnection datasource = new DatabaseConnection(this);
            datasource.open();
            Course cm = datasource.getNotes(courseId);

            //Assign to proper controls
            ptNotesName.setText(cm.getCourseNotesTitle());
            etNotesMultiText.setText(cm.getCourseNotesText());
            DatabaseConnection.databaseHelper.close();
        }
    }

    //Appbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get id of item selected in menu
        int id = item.getItemId();

        if (id == R.id.menuShare) {
            notesName = ptNotesName.getText().toString();
            notesBody = etNotesMultiText.getText().toString();

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, notesName);
            sendIntent.putExtra(Intent.EXTRA_TEXT, notesBody);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNote(View view) {
        //Create variables
        notesName = ptNotesName.getText().toString();
        notesBody = etNotesMultiText.getText().toString();

        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        datasource.updateNotes(courseId, notesName, notesBody);
        DatabaseConnection.databaseHelper.close();
        finish();
    }
}
