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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mTypeSpinner;
    private long courseId;
    private long assessmentId;
    public EditText assessmentNameEditText;

    private EditText mGoalDate;
    private DatePickerDialog.OnDateSetListener mGoalDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentNameEditText = findViewById(R.id.ptAssessmentDetailName);
        mGoalDate = findViewById(R.id.tvAssessmentDetailGoalDate);
        mTypeSpinner = findViewById(R.id.spinnerAssessmentDetailType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(this);

        Bundle extras;
        extras = getIntent().getExtras();

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

            assessmentNameEditText.setText(assessmentName);
            mGoalDate.setText(assessmentGoal);

            int typePosition = adapter.getPosition(assessmentType);
            mTypeSpinner.setSelection(typePosition);
        }

        mGoalDate.setOnClickListener(new View.OnClickListener() {
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
                dialog = new DatePickerDialog(AssessmentDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mGoalDateSetListener,
                        year,
                        month,
                        day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mGoalDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date;
                date = month + "/" + day + "/" + year;

                mGoalDate.setText(date);
            }
        };
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
        assessmentGoal = mGoalDate.getText().toString();

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

        String assessmentName;
        assessmentName = assessmentNameEditText.getText().toString();

        String assessmentGoal;
        assessmentGoal = mGoalDate.getText().toString();

        String assessmentType;
        assessmentType = mTypeSpinner.getSelectedItem().toString();

        final Assessment assessment = new Assessment();

        assessment.setCourseId(courseId);
        assessment.setAssessmentId(assessmentId);
        assessment.setAssessmentName(assessmentName);
        assessment.setAssessmentGoalDate(assessmentGoal);
        assessment.setAssessmentType(assessmentType);


        DatabaseConnection datasource;
        datasource = new DatabaseConnection(this);

        datasource.open();

        if (assessmentId != 0) datasource.updateAssessment(assessment);
        else datasource.createAssessment(assessment);


        DatabaseConnection.databaseHelper.close();

        finish();
    }

    public void deleteAssessment(View view) {
        DatabaseConnection datasource;
        datasource = new DatabaseConnection(this);

        datasource.open();

        datasource.deleteAssessment(assessmentId);

        DatabaseConnection.databaseHelper.close();

        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}