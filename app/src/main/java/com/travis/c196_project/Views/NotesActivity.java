package com.travis.c196_project.Views;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.R;

public class NotesActivity extends Activity {

    public EditText ptNotesName;
    public EditText etNotesMultiText;
    private long courseId;
    String notesName;
    String notesBody;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ptNotesName = findViewById(R.id.ptNotesName);
        etNotesMultiText = findViewById(R.id.etNotesMultiText);

        Bundle extras;
        extras = getIntent().getExtras();

        if (extras == null) {
            return;
        }
        courseId = extras.getLong("courseId");

        CourseData courseData = new CourseData(this);

        courseData.open();

        Course cm;
        cm = courseData.getNotes(courseId);

        ptNotesName.setText(cm.getCourseNotesTitle());
        etNotesMultiText.setText(cm.getCourseNotesText());

        courseData.close();
    }


    public void saveNote(View view) {
        notesName = ptNotesName.getText().toString();
        notesBody = etNotesMultiText.getText().toString();

        CourseData courseData = new CourseData(this);

        courseData.open();

        courseData.updateNotes(courseId, notesName, notesBody);

        courseData.close();

        finish();
    }


}
