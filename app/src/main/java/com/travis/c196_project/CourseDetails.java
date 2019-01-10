package com.travis.c196_project;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mStatusSpinner;
    private long termId;
    private long courseId;
    public EditText courseNameEditText;

    public EditText ptMentorDetailName;
    public EditText ptMentorDetailPhone;
    public EditText ptMentorDetailEmail;

    //DatePicker
    private EditText mCourseStartDate;
    private EditText mCourseEndDate;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener;
    private DatePickerDialog.OnDateSetListener mEndDateSetListener;

//    private static final String TAG = "CourseDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        findViewById(R.id.btnCourseDetailNotes).setOnClickListener(buttonClickListener);
        findViewById(R.id.btnCourseDetailManageAss).setOnClickListener(buttonClickListener);

        //variables for controls
        courseNameEditText = findViewById(R.id.ptCourseDetailCourseName);
        mCourseStartDate = findViewById(R.id.tvCourseDetailStartDate);
        mCourseEndDate = findViewById(R.id.tvCourseDetailEndDate);
        mStatusSpinner = findViewById(R.id.spinnerCourseDetailStatus);
        ptMentorDetailName = findViewById(R.id.ptMentorDetailName);
        ptMentorDetailPhone = findViewById(R.id.ptMentorDetailPhone);
        ptMentorDetailEmail = findViewById(R.id.ptMentorDetailEmail);

        //Spinner
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.course_detail, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(adapter);
        mStatusSpinner.setOnItemSelectedListener(this);

        Bundle extras;
        extras = getIntent().getExtras();
        if (null == extras) {
        } else {
            termId = extras.getLong("termId");
            courseId = extras.getLong("courseId");
            String courseName;
            courseName = extras.getString("courseName");
            String courseStart;
            courseStart = extras.getString("courseStart");
            String courseEnd;
            courseEnd = extras.getString("courseEnd");
            String courseStatus;
            courseStatus = extras.getString("courseStatus");
            String mentorName;
            mentorName = extras.getString("mentorName");
            String mentorPhone;
            mentorPhone = extras.getString("mentorPhone");
            String mentorEmail;
            mentorEmail = extras.getString("mentorEmail");

            //Assign to proper controls
            courseNameEditText.setText(courseName);
            mCourseStartDate.setText(courseStart);
            mCourseEndDate.setText(courseEnd);
            ptMentorDetailName.setText(mentorName);
            ptMentorDetailPhone.setText(mentorPhone);
            ptMentorDetailEmail.setText(mentorEmail);

            int statusPosition;
            statusPosition = adapter.getPosition(courseStatus);

            mStatusSpinner.setSelection(statusPosition);
        }

        //DatePicker
        mCourseStartDate.setOnClickListener(new View.OnClickListener() {
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
                        CourseDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mStartDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mCourseEndDate.setOnClickListener(new View.OnClickListener() {
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
                        CourseDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mEndDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date;
                date = month + "/" + day + "/" + year;
                mCourseStartDate.setText(date);
            }
        };

        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //January = 0 need to add 1 to get correct month
                month += 1;
                String date;
                date = month + "/" + day + "/" + year;
                mCourseEndDate.setText(date);
            }
        };
    }

    public void setStartAlert() throws ParseException {
        AlarmManager mAlarm;
        mAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent mIntent;
        mIntent = new Intent(this, NotificationReceiver.class);

        PendingIntent notifyIntent;
        notifyIntent = PendingIntent.getBroadcast(this, 2, mIntent, 0);

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM/dd/yyyy");

        String assessmentGoal;
        assessmentGoal = mCourseStartDate.getText().toString();

        Date goalDate;
        goalDate = sdf.parse(assessmentGoal);

        //initiate a Switch
        Switch switchGoalAlert;
        switchGoalAlert = findViewById(R.id.ptCourseDetailStartAlert);

        // check current state of a Switch
        boolean switchState;
        if (switchGoalAlert.isChecked()) switchState = true;
        else switchState = false;

        if (!switchState) {
            mAlarm.cancel(notifyIntent);
        } else {
            long triggerAtMillis;
            triggerAtMillis = goalDate.getTime();
            mAlarm.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, notifyIntent);
        }

    }


    public void setEndAlert() throws ParseException {
        AlarmManager mAlarm;
        mAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent mIntent;
        mIntent = new Intent(this, NotificationReceiver.class);

        PendingIntent notifyIntent;
        notifyIntent = PendingIntent.getBroadcast(this, 3, mIntent, 0);

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM/dd/yyyy");

        String assessmentGoal;
        assessmentGoal = mCourseEndDate.getText().toString();

        Date goalDate;
        goalDate = sdf.parse(assessmentGoal);

        //initiate a Switch
        Switch switchGoalAlert;
        switchGoalAlert = findViewById(R.id.ptCourseDetailEndAlert);

        // check current state of a Switch
        boolean switchState;
        if (switchGoalAlert.isChecked()) switchState = true;
        else switchState = false;

        if (!switchState) {
            mAlarm.cancel(notifyIntent);
        } else {
            long triggerAtMillis;
            triggerAtMillis = goalDate.getTime();
            mAlarm.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, notifyIntent);
        }

    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnCourseDetailNotes:

                    if (courseId == 0) {
                        Toast.makeText(getApplicationContext(),
                                "You must save a course before adding notes", Toast.LENGTH_LONG).show();
                    } else {
                        Intent openNotes;
                        openNotes = new Intent(CourseDetails.this, NotesActivity.class);
                        Bundle extras;
                        extras = new Bundle();
                        extras.putLong("courseId", courseId);
                        openNotes.putExtras(extras);

                        extras.putLong("courseId", courseId);
                        startActivity(openNotes);
                    }
                    break;

                case R.id.btnCourseDetailManageAss:

                    if (courseId == 0) {
                        Toast.makeText(getApplicationContext(),
                                "You must save a course before adding assessments", Toast.LENGTH_LONG).show();
                    } else {
                        Intent openAssessment;
                        openAssessment = new Intent(CourseDetails.this, AssessmentList.class);
                        Bundle extras;
                        extras = new Bundle();
                        extras.putLong("courseId", courseId);
                        openAssessment.putExtras(extras);

                        startActivity(openAssessment);
                    }
                    break;
            }
        }
    };

    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        adapterView.getItemAtPosition(position).toString();
    }

    //Spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void saveCourse(View view) throws ParseException {
        setStartAlert();
        setEndAlert();
        //Create variables
        String courseName;
        courseName = courseNameEditText.getText().toString();
        String courseStart;
        courseStart = mCourseStartDate.getText().toString();
        String courseEnd;
        courseEnd = mCourseEndDate.getText().toString();
        String courseStatus;
        courseStatus = mStatusSpinner.getSelectedItem().toString();
        String mentorName;
        mentorName = ptMentorDetailName.getText().toString();
        String mentorPhone;
        mentorPhone = ptMentorDetailPhone.getText().toString();
        String mentorEmail;
        mentorEmail = ptMentorDetailEmail.getText().toString();

        //set variables with data
        final Course course;
        course = new Course();
        course.setTermId(termId);
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setCourseStart(courseStart);
        course.setCourseEnd(courseEnd);
        course.setCourseStatus(courseStatus);
        course.setCourseMentorName(mentorName);
        course.setCourseMentorPhone(mentorPhone);
        course.setCourseMentorEmail(mentorEmail);

        DatabaseConnection datasource;
        datasource = new DatabaseConnection(this);
        datasource.open();

        if (courseId == 0) datasource.createCourse(course);
        else datasource.updateCourse(course);

        DatabaseConnection.databaseHelper.close();
        finish();
    }

    public void deleteCourse(View view) {

        DatabaseConnection datasource;
        datasource = new DatabaseConnection(this);
        datasource.open();
        datasource.deleteCourse(courseId);
        DatabaseConnection.databaseHelper.close();
        finish();

    }
}
