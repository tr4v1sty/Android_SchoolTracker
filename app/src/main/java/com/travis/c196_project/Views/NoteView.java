package com.travis.c196_project.Views;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.R;

public class NoteView extends Activity {

    public EditText noteDetail;
    private long courseId;
    String notesDetailString;

    public void shareNote(View view) {

        notesDetailString = noteDetail.getText().toString();

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, notesDetailString);
        sendIntent.setType("text/plain");

        startActivity(sendIntent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteDetail = findViewById(R.id.etNotesMultiText);

        Bundle extras;
        extras = getIntent().getExtras();

        setupFields(extras);
    }

    private void setupFields(Bundle extras) {
        if (extras == null) {
            return;
        }
        courseId = extras.getLong("courseId");

        CourseData courseData = new CourseData(this);
        courseData.open();

        Course cm;
        cm = courseData.getNotes(courseId);

        noteDetail.setText(cm.getCourseNotesText());

        courseData.close();
    }

    public void saveNote(View view) {
        notesDetailString = noteDetail.getText().toString();

        CourseData courseData = new CourseData(this);

        courseData.open();

        courseData.updateNotes(courseId, notesDetailString);

        courseData.close();

        finish();
    }
}
