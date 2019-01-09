package com.travis.c196_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    public Button btnTermDetailsSave;
    public Button btnTermDetailsAddCourse;
    public EditText termNameEditText;
    private long termId;

    //DatePicker
    private EditText mTermStartDate;
    private EditText mTermEndDate;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;

    private static final String TAG = "TermDetails";


    public void addCourse() {
        btnTermDetailsAddCourse = findViewById(R.id.btnTermDetailsAddCourse);
        btnTermDetailsAddCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (termId == 0) {
                    Toast.makeText(getApplicationContext(),
                            "You must save a term before adding courses", Toast.LENGTH_LONG).show();
                } else {
                    Intent addCourse = new Intent(TermDetails.this, CourseList.class);
                    Bundle extras = new Bundle();
                    extras.putLong("termId", termId);
                    addCourse.putExtras(extras);
                    if (extras != null) {
                        extras.putLong("termId", termId);

                        startActivity(addCourse);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        addCourse();

        //Variables for the controls
        termNameEditText = findViewById(R.id.ptTermDetailsName);
        mTermStartDate = findViewById(R.id.tvTermDetailsStart);
        mTermEndDate = findViewById(R.id.tvTermDetailsEnd);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            termId = extras.getLong("termId");
            String termName = extras.getString("termName");
            String termStart = extras.getString("termStart");
            String termEnd = extras.getString("termEnd");

            //Assign to proper controls
            termNameEditText.setText(termName);
            mTermStartDate.setText(termStart);
            mTermEndDate.setText(termEnd);
        }

        mTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            //get today's date
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TermDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mStartDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTermEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TermDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mEndDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mTermStartDate.setText(date);
            }
        };

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //January = 0 need to add 1 to get correct month
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mTermEndDate.setText(date);
            }
        };
    }

    public void saveTerm(View view) {
        //Create variables
        String termName = termNameEditText.getText().toString();
        String termStart = mTermStartDate.getText().toString();
        String termEnd = mTermEndDate.getText().toString();

        final Term term = new Term();
        term.setTermId(termId);
        term.setTermName(termName);
        term.setTermStart(termStart);
        term.setTermEnd(termEnd);

        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            datasource.createTerm(term);
        } else {
            datasource.updateTerm(term);
        }


        DatabaseConnection.databaseHelper.close();
        finish();
    }

    public void deleteTerm(View view) {
        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        List<Course> listValue = datasource.getCourses(termId);
        if (listValue.isEmpty()) {
            datasource.deleteTerm(termId);

        } else {
            Toast.makeText(this, "Cannot delete a term with courses associated to it", Toast.LENGTH_SHORT).show();
        }

        DatabaseConnection.databaseHelper.close();
        finish();
    }
}