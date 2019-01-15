package com.travis.c196_project.Views;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.R;

import java.util.List;

public class CourseList extends ListActivity {
    public Button btnAddCourse;
    private long termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourse;
                addCourse = new Intent(CourseList.this, CourseDetails.class);

                addCourse.putExtra("termId", termId);

                startActivity(addCourse);
            }
        });

        Bundle extras;
        extras = getIntent().getExtras();

        termId = extras.getLong("termId");

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(CourseList.this, CourseDetails.class);

                Course course;
                course = (Course) parent.getItemAtPosition(position);

                intent.putExtra("termId", course.getCourseTermId());
                intent.putExtra("courseId", course.getCourseId());
                intent.putExtra("courseName", course.getCourseName());
                intent.putExtra("courseStart", course.getCourseStart());
                intent.putExtra("courseEnd", course.getCourseEnd());
                intent.putExtra("courseStatus", course.getCourseStatus());

                intent.putExtra("mentorName", course.getCourseMentorName());
                intent.putExtra("mentorPhone", course.getCourseMentorPhone());
                intent.putExtra("mentorEmail", course.getCourseMentorEmail());

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

        CourseData courseData = new CourseData(this);

        courseData.open();

        List<Course> listValue = courseData.getCourses(termId);

        courseData.close();

        ArrayAdapter<Course> adapter;
        adapter = new ArrayAdapter<>(this,
                R.layout.list_items,
                listValue);

        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}