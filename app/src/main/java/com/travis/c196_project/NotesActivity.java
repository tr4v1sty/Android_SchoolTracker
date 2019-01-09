package com.travis.c196_project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NotesActivity extends Activity {

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

    public void shareNote(View view) {

        notesName = ptNotesName.getText().toString();
        notesBody = etNotesMultiText.getText().toString();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, notesName);
        sendIntent.putExtra(Intent.EXTRA_TEXT, notesBody);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }
}
