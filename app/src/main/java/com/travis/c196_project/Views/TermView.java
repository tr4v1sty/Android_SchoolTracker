package com.travis.c196_project.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import java.util.Calendar;
import java.util.List;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Data.TermData;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.Models.Term;
import com.travis.c196_project.R;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class TermView extends AppCompatActivity {
    private long termId;

    private EditText termStartDate;
    private EditText termEndDate;
    private DatePickerDialog.OnDateSetListener termStartDateListener;
    private DatePickerDialog.OnDateSetListener termEndDateListener;

    public Button manageCoursesButton;

    public EditText termName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        manageCoursesButton = findViewById(R.id.manageCoursesButtonId);

        manageCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (termId != 0) {
                    Intent addCourse;
                    addCourse = new Intent(TermView.this, CourseList.class);

                    Bundle extras;
                    extras = new Bundle();

                    extras.putLong("termId", termId);
                    addCourse.putExtras(extras);
                    extras.putLong("termId", termId);

                    startActivity(addCourse);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "You must save a term before adding courses",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        termName = findViewById(R.id.ptTermDetailsName);
        termStartDate = findViewById(R.id.tvTermDetailsStart);
        termEndDate = findViewById(R.id.tvTermDetailsEnd);

        Bundle extras;
        extras = getIntent().getExtras();

        setupFields(extras);

        termStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            //get today's date
            public void onClick(View view) {
                Calendar cal;
                cal = Calendar.getInstance();

                int year;
                year = cal.get(Calendar.YEAR);

                int month;
                month = cal.get(Calendar.MONTH);

                int day;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(
                        TermView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        termStartDateListener,
                        year,
                        month,
                        day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        termStartDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date;
                date = month + "/" + day + "/" + year;

                termStartDate.setText(date);
            }
        };

        termEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal;
                cal = Calendar.getInstance();

                int year;
                year = cal.get(Calendar.YEAR);

                int month;
                month = cal.get(Calendar.MONTH);

                int day;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(
                        TermView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        termEndDateListener,
                        year,
                        month,
                        day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        termEndDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //January = 0 need to add 1 to get correct month
                month += 1;

                String date;
                date = month + "/" + day + "/" + year;

                termEndDate.setText(date);
            }
        };
    }

    private void setupFields(Bundle extras) {
        if (extras == null) {
            //do nothing
        } else {
            termId = extras.getLong("termId");

            String termName;
            termName = extras.getString("termName");

            String termStart;
            termStart = extras.getString("termStart");

            String termEnd;
            termEnd = extras.getString("termEnd");

            this.termName.setText(termName);
            termStartDate.setText(termStart);
            termEndDate.setText(termEnd);
        }
    }

    public void saveTerm(View view) {
        final Term term = new Term();

        term.setTermId(termId);

        String termName;
        termName = this.termName.getText().toString();
        term.setTermName(termName);

        String termStart;
        termStart = termStartDate.getText().toString();
        term.setTermStart(termStart);

        String termEnd;
        termEnd = termEndDate.getText().toString();
        term.setTermEnd(termEnd);

        TermData termData = new TermData(this);

        termData.open();

        Bundle extras;
        extras = getIntent().getExtras();

        if (extras != null) {
            termData.updateTerm(term);
        } else {
            termData.createTerm(term);
        }

        termData.close();

        finish();
    }

    public void deleteTerm(View view) {

        TermData termData = new TermData(this);
        termData.open();

        CourseData courseData = new CourseData(this);
        courseData.open();

        List<Course> listValue;
        listValue = courseData.getCourses(termId);

        if (listValue.isEmpty()) {
            termData.deleteTerm(termId);
        } else {
            Toast.makeText(this,
                    "ERROR: You must first delete all associated courses",
                    Toast.LENGTH_LONG).show();
        }

        courseData.close();

        finish();
    }
}