package com.travis.c196_project.Views;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.travis.c196_project.Data.AssessmentData;
import com.travis.c196_project.Models.Assessment;
import com.travis.c196_project.R;
import com.travis.c196_project.Utilities.NotificationReceiver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class AssessmentView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long courseId;
    private long assessmentId;

    public EditText assessmentName;

    private Spinner assessmentType;

    private EditText assessmentDate;
    private DatePickerDialog.OnDateSetListener assessmentDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(
                this, R.array.type_of_assessment, android.R.layout.simple_spinner_item);

        assessmentName = findViewById(R.id.ptAssessmentDetailName);
        assessmentDate = findViewById(R.id.tvAssessmentDetailGoalDate);
        assessmentType = findViewById(R.id.spinnerAssessmentDetailType);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentType.setAdapter(adapter);
        assessmentType.setOnItemSelectedListener(this);

        Bundle extras;
        extras = getIntent().getExtras();

        setupFields(adapter, extras);

        assessmentDate.setOnClickListener(new View.OnClickListener() {
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
                dialog = new DatePickerDialog(AssessmentView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        assessmentDateListener,
                        year,
                        month,
                        day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        assessmentDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date;
                date = month + "/" + day + "/" + year;

                assessmentDate.setText(date);
            }
        };
    }

    private void setupFields(ArrayAdapter<CharSequence> adapter, Bundle extras) {
        if (extras == null) {
            //do nothing
        } else {

            courseId = extras.getLong("courseId");
            assessmentId = extras.getLong("assessmentId");

            String assessmentName;
            assessmentName = extras.getString("assessmentName");
            String assessmentGoal;
            assessmentGoal = extras.getString("assessmentGoalDate");
            String assessmentType;
            assessmentType = extras.getString("assessmentType");

            this.assessmentName.setText(assessmentName);
            assessmentDate.setText(assessmentGoal);

            int typePosition = adapter.getPosition(assessmentType);
            this.assessmentType.setSelection(typePosition);
        }
    }

    public void setAlert() throws ParseException {
        AlarmManager mAlarm;
        mAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent mIntent;
        mIntent = new Intent(this, NotificationReceiver.class);

        PendingIntent notifyIntent;
        notifyIntent = PendingIntent.getBroadcast(this, 1, mIntent, 0);

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM/dd/yyyy");

        String assessmentGoal;
        assessmentGoal = assessmentDate.getText().toString();

        Date goalDate;
        goalDate = sdf.parse(assessmentGoal);

        Switch switchGoalAlert;
        switchGoalAlert = findViewById(R.id.ptAssessmentDetailGoalAlert);

        boolean switchState;
        switchState = switchGoalAlert.isChecked();

        if (!switchState) {
            mAlarm.cancel(notifyIntent);
        } else {
            long triggerAtMillis;
            triggerAtMillis = goalDate.getTime();

            mAlarm.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, notifyIntent);
        }
    }

    public void saveAssessment(View view) throws ParseException {
        setAlert();

        final Assessment assessment = new Assessment();

        assessment.setCourseId(courseId);
        assessment.setAssessmentId(assessmentId);

        String assessmentName;
        assessmentName = this.assessmentName.getText().toString();
        assessment.setAssessmentName(assessmentName);

        String assessmentGoal;
        assessmentGoal = assessmentDate.getText().toString();
        assessment.setAssessmentGoalDate(assessmentGoal);

        String assessmentType;
        assessmentType = this.assessmentType.getSelectedItem().toString();
        assessment.setAssessmentType(assessmentType);

        AssessmentData assessmentData = new AssessmentData(this);

        assessmentData.open();

        if (assessmentId != 0) {
            assessmentData.updateAssessment(assessment);
        } else {
            assessmentData.createAssessment(assessment);
        }

        assessmentData.close();

        finish();
    }

    public void deleteAssessment(View view) {
        AssessmentData assessmentData = new AssessmentData(this);

        assessmentData.open();

        assessmentData.deleteAssessment(assessmentId);

        assessmentData.close();

        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}