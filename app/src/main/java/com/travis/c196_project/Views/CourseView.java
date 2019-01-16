package com.travis.c196_project.Views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.R;
import com.travis.c196_project.Utilities.NotificationReceiver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class CourseView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner courseStatus;
    private long termId;
    private long courseId;
    public EditText courseName;

    public EditText mentorName;
    public EditText mentorPhone;
    public EditText mentorEmail;

    private EditText courseStartDate;
    private EditText courseEndDate;
    private DatePickerDialog.OnDateSetListener courseStartDateListener;
    private DatePickerDialog.OnDateSetListener courseEndDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        findViewById(R.id.btnCourseDetailNotes).setOnClickListener(buttonClickListener);
        findViewById(R.id.btnCourseDetailManageAss).setOnClickListener(buttonClickListener);

        courseName = findViewById(R.id.ptCourseDetailCourseName);
        courseStartDate = findViewById(R.id.tvCourseDetailStartDate);
        courseEndDate = findViewById(R.id.tvCourseDetailEndDate);
        courseStatus = findViewById(R.id.spinnerCourseDetailStatus);
        mentorName = findViewById(R.id.ptMentorDetailName);
        mentorPhone = findViewById(R.id.ptMentorDetailPhone);
        mentorEmail = findViewById(R.id.ptMentorDetailEmail);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_course_spinner,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatus.setAdapter(adapter);
        courseStatus.setOnItemSelectedListener(this);

        Bundle extras;
        extras = getIntent().getExtras();

        setupFields(adapter, extras);

        courseStartDate.setOnClickListener(new View.OnClickListener() {
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
                        CourseView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        courseStartDateListener,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        courseStartDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date;
                date = month + "/" + day + "/" + year;

                courseStartDate.setText(date);
            }
        };

        courseEndDate.setOnClickListener(new View.OnClickListener() {
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
                        CourseView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        courseEndDateListener,
                        year,
                        month,
                        day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        courseEndDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date;
                date = month + "/" + day + "/" + year;

                courseEndDate.setText(date);
            }
        };
    }

    private void setupFields(ArrayAdapter<CharSequence> adapter, Bundle extras) {
        if (extras == null) {
            //do nothing
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

            this.courseName.setText(courseName);
            courseStartDate.setText(courseStart);
            courseEndDate.setText(courseEnd);
            this.mentorName.setText(mentorName);
            this.mentorPhone.setText(mentorPhone);
            this.mentorEmail.setText(mentorEmail);

            int statusPosition;
            statusPosition = adapter.getPosition(courseStatus);

            this.courseStatus.setSelection(statusPosition);
        }
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
        assessmentGoal = courseStartDate.getText().toString();

        Date goalDate;
        goalDate = sdf.parse(assessmentGoal);

        Switch switchGoalAlert;
        switchGoalAlert = findViewById(R.id.ptCourseDetailStartAlert);

        boolean switchState;
        if (switchGoalAlert.isChecked()) switchState = true;
        else switchState = false;

        if (switchState == false) {
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
        assessmentGoal = courseEndDate.getText().toString();

        Date goalDate;
        goalDate = sdf.parse(assessmentGoal);

        Switch switchGoalAlert;
        switchGoalAlert = findViewById(R.id.ptCourseDetailEndAlert);

        boolean switchState;
        if (switchGoalAlert.isChecked()) switchState = true;
        else switchState = false;

        if (switchState == false) {
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

            int i = view.getId();

            if (i == R.id.btnCourseDetailNotes) {
                if (courseId == 0) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR: You must first save this course ",
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    Intent openNotes;
                    openNotes = new Intent(CourseView.this, NoteView.class);

                    Bundle extras;
                    extras = new Bundle();

                    extras.putLong("courseId", courseId);
                    openNotes.putExtras(extras);
                    extras.putLong("courseId", courseId);

                    startActivity(openNotes);
                }

            } else if (i == R.id.btnCourseDetailManageAss) {
                if (courseId == 0) {
                    Toast.makeText(getApplicationContext(),
                            "ERROR: You must first delete all associated assessments",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent openAssessment;
                    openAssessment = new Intent(CourseView.this, AssessmentList.class);
                    Bundle extras;
                    extras = new Bundle();
                    extras.putLong("courseId", courseId);
                    openAssessment.putExtras(extras);

                    startActivity(openAssessment);
                }

            }
        }
    };

    //onItemSelected and onNothingSelected are required for Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        adapterView.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void saveCourse(View view) throws ParseException {
        setStartAlert();
        setEndAlert();

        String courseName;
        courseName = this.courseName.getText().toString();
        String courseStart;
        courseStart = courseStartDate.getText().toString();
        String courseEnd;
        courseEnd = courseEndDate.getText().toString();
        String courseStatus;
        courseStatus = this.courseStatus.getSelectedItem().toString();
        String mentorName;
        mentorName = this.mentorName.getText().toString();
        String mentorPhone;
        mentorPhone = this.mentorPhone.getText().toString();
        String mentorEmail;
        mentorEmail = this.mentorEmail.getText().toString();

        final Course course = new Course();

        course.setTermId(termId);
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setCourseStart(courseStart);
        course.setCourseEnd(courseEnd);
        course.setCourseStatus(courseStatus);
        course.setCourseMentorName(mentorName);
        course.setCourseMentorPhone(mentorPhone);
        course.setCourseMentorEmail(mentorEmail);

        CourseData courseData = new CourseData(this);

        courseData.open();

        if (courseId == 0) courseData.createCourse(course);
        else courseData.updateCourse(course);

        courseData.close();

        finish();
    }

    public void deleteCourse(View view) {

        CourseData courseData = new CourseData(this);

        courseData.open();

        courseData.deleteCourse(courseId);

        courseData.close();

        finish();

    }
}
