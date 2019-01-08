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
import android.util.Log;
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

public class AssessmentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mTypeSpinner;
    private long courseId;
    private long assessmentId;
    public EditText assessmentNameEditText;


    //DatePicker
    private EditText mGoalDate;
    private DatePickerDialog.OnDateSetListener mGoalDateSetListener;

    private static final String TAG = "AssessmentDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        //variables for controls
        assessmentNameEditText = findViewById(R.id.ptAssessmentDetailName);
        mGoalDate = findViewById(R.id.tvAssessmentDetailGoalDate);
        mTypeSpinner = findViewById(R.id.spinnerAssessmentDetailType);


        //Spinner
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            courseId = extras.getLong("courseId");
            assessmentId = extras.getLong("assessmentId");
            String assessmentName = extras.getString("assessmentName");
            String assessmentGoal = extras.getString("assessmentGoalDate");
            String assessmentType = extras.getString("assessmentType");

            //Assign to proper controls
            assessmentNameEditText.setText(assessmentName);
            mGoalDate.setText(assessmentGoal);

            int typePosition = adapter.getPosition(assessmentType);
            mTypeSpinner.setSelection(typePosition);
        }

        //DatePicker
        mGoalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            //get today's date
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AssessmentDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mGoalDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mGoalDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mGoalDate.setText(date);
            }
        };
    }

    public void setAlert() {
        try {
            AlarmManager mAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent mIntent = new Intent(this, NotificationReceiver.class);

            PendingIntent notifyIntent = PendingIntent.getBroadcast(this, 1, mIntent, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String assessmentGoal = mGoalDate.getText().toString();
            Date goalDate = sdf.parse(assessmentGoal);

            //initiate a Switch
            Switch switchGoalAlert = findViewById(R.id.ptAssessmentDetailGoalAlert);
            // check current state of a Switch (true or false).
            boolean switchState = switchGoalAlert.isChecked();

            if (switchState) {
                long triggerAtMillis = goalDate.getTime();
                mAlarm.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, notifyIntent);
            } else {
                mAlarm.cancel(notifyIntent);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //Appbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get id of item selected in menu
        int id = item.getItemId();

        if (id == R.id.menuDelete) {
            DatabaseConnection datasource = new DatabaseConnection(this);
            datasource.open();
            datasource.deleteAssessment(assessmentId);
            DatabaseConnection.databaseHelper.close();
            finish();

            Toast.makeText(this, "Assessment was deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String sSelected = adapterView.getItemAtPosition(position).toString();
    }

    //Spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void saveAssessment(View view) {
        setAlert();
        //Create variables
        String assessmentName = assessmentNameEditText.getText().toString();
        String assessmentGoal = mGoalDate.getText().toString();
        String assessmentType = mTypeSpinner.getSelectedItem().toString();

        //set variables with data
        final Assessment assessment = new Assessment();
        assessment.setCourseId(courseId);
        assessment.setAssessmentId(assessmentId);
        assessment.setAssessmentName(assessmentName);
        assessment.setAssessmentGoalDate(assessmentGoal);
        assessment.setAssessmentType(assessmentType);


        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
//        Bundle extras = getIntent().getExtras();

        if (assessmentId == 0) {
            datasource.createAssessment(assessment);
        } else {
            datasource.updateAssessment(assessment);
        }


        DatabaseConnection.databaseHelper.close();
        finish();
    }
}