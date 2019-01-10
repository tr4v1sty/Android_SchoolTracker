package com.travis.c196_project;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class AssessmentList extends ListActivity {
    public Button btnAddAssessment;
    private long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        btnAddAssessment = findViewById(R.id.btnAddAssessment);
        btnAddAssessment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addAssessment = new Intent(AssessmentList.this, AssessmentDetails.class);
                addAssessment.putExtra("courseId", courseId);

                startActivity(addAssessment);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getLong("courseId");
        }

        //Send selected list item to AssessmentDetails
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
                Assessment assessment = (Assessment) parent.getItemAtPosition(position);

                //get info
                intent.putExtra("courseId", assessment.getCourseId());
                intent.putExtra("assessmentId", assessment.getAssessmentId());
                intent.putExtra("assessmentName", assessment.getAssessmentName());
                intent.putExtra("assessmentGoalDate", assessment.getAssessmentGoalDate());
                intent.putExtra("assessmentType", assessment.getAssessmentType());

                startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        ArrayAdapter<Assessment> adapter = (ArrayAdapter<Assessment>) getListAdapter();
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        List<Assessment> listValue = datasource.getAssessments(courseId);

        DatabaseConnection.databaseHelper.close();
        ArrayAdapter<Assessment> adapter = new ArrayAdapter<>(this,
                R.layout.list_items, listValue);
        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}