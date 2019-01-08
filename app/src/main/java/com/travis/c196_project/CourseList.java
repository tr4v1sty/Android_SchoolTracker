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

public class CourseList extends ListActivity {
    public Button btnAddCourse;
    private long termId;

    public void configAddCourse() {
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addCourse = new Intent(CourseList.this, CourseDetails.class);
                addCourse.putExtra("termId", termId);

                startActivity(addCourse);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        configAddCourse();

        Bundle extras = getIntent().getExtras();
        termId = extras.getLong("termId");

        //Send selected list item to CourseDetails
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                Course course = (Course) parent.getItemAtPosition(position);

                //get info
                intent.putExtra("termId", course.getCourseTermId());
                intent.putExtra("courseId", course.getCourseId());
                intent.putExtra("courseName", course.getCourseName());
                intent.putExtra("courseStart", course.getCourseStart());
                intent.putExtra("courseEnd", course.getCourseEnd());
                intent.putExtra("courseStatus", course.getCourseStatus());

                intent.putExtra("mentorName", course.getCourseMentorName());
                intent.putExtra("mentorPhone", course.getCourseMentorPhone());
                intent.putExtra("mentorEmail", course.getCourseMentorEmail());
//                intent.putExtra("mentor2Name", course.getCourseMentorTwo());
//                intent.putExtra("mentor2Phone", course.getCourseMentorPhoneTwo());
//                intent.putExtra("mentor2Email", course.getCourseMentorEmailTwo());

                startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        ArrayAdapter<Course> adapter = (ArrayAdapter<Course>) getListAdapter();
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        List<Course> listValue = datasource.getCourses(termId);
        DatabaseConnection.databaseHelper.close();
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this,
                R.layout.list_items, listValue);
        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}