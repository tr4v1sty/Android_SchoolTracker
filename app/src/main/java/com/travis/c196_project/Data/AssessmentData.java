package com.travis.c196_project.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travis.c196_project.Models.Assessment;
import com.travis.c196_project.Utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AssessmentData {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public AssessmentData(Context context) { dbHelper = new DBHelper(context); }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }


    private static final String [] columns_assessments = {
            DBHelper.ASSESSMENT_COURSE_ID_COLUMN,
            DBHelper.ASSESSMENT_GOAL_DATE_COLUMN,
            DBHelper.ASSESSMENT_NAME_COLUMN,
            DBHelper.ASSESSMENT_NOTIFICATION_COLUMN,
            DBHelper.ASSESSMENT_TABLE_ID_COLUMN,
            DBHelper.ASSESSMENT_TYPE_COLUMN,

    };

    public Assessment createAssessment(Assessment assessment) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.ASSESSMENT_COURSE_ID_COLUMN,
                assessment.getCourseId());
        values.put(DBHelper.ASSESSMENT_NAME_COLUMN,
                assessment.getAssessmentName());
        values.put(DBHelper.ASSESSMENT_TYPE_COLUMN,
                assessment.getAssessmentType());
        values.put(DBHelper.ASSESSMENT_NOTIFICATION_COLUMN,
                assessment.getAssessmentNotification());
        values.put(DBHelper.ASSESSMENT_GOAL_DATE_COLUMN,
                assessment.getAssessmentGoalDate());

        long insertId;
        insertId = database.insert(DBHelper.ASSESSMENTS_TABLE,
                null, values);

        assessment.setAssessmentId(insertId);

        return assessment;
    }


    public void updateAssessment(Assessment assessment) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.ASSESSMENT_TABLE_ID_COLUMN,
                assessment.getAssessmentId());
        values.put(DBHelper.ASSESSMENT_NAME_COLUMN,
                assessment.getAssessmentName());
        values.put(DBHelper.ASSESSMENT_TYPE_COLUMN,
                assessment.getAssessmentType());
        values.put(DBHelper.ASSESSMENT_NOTIFICATION_COLUMN,
                assessment.getAssessmentNotification());
        values.put(DBHelper.ASSESSMENT_GOAL_DATE_COLUMN,
                assessment.getAssessmentGoalDate());

        database.update(DBHelper.ASSESSMENTS_TABLE,
                values,
                DBHelper.ASSESSMENT_TABLE_ID_COLUMN + "=" + assessment.getAssessmentId(),
                null);
    }

    public void deleteAssessment(long id) {

        database.delete(DBHelper.ASSESSMENTS_TABLE,
                DBHelper.ASSESSMENT_TABLE_ID_COLUMN
                        + " = " + id,
                null);

    }

    public List<Assessment> getAssessments(long courseId) {

        List<Assessment> assessmentList = new ArrayList<>();

        String[] selectionArgs = new String[]{Long.toString(courseId)};

        Cursor cursor;
        cursor = database.query(DBHelper.ASSESSMENTS_TABLE,
                columns_assessments,
                DBHelper.ASSESSMENT_COURSE_ID_COLUMN + " = ?",
                selectionArgs,
                null,
                null,
                null);

        cursorGetAssessment(assessmentList, cursor);

        return assessmentList;
    }

    private void cursorGetAssessment(List<Assessment> assessmentList, Cursor cursor) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Assessment assessment = new Assessment();
                assessment.setAssessmentId(cursor.getLong(cursor.getColumnIndex(DBHelper.ASSESSMENT_TABLE_ID_COLUMN)));
                assessment.setCourseId(cursor.getLong(cursor.getColumnIndex(DBHelper.ASSESSMENT_COURSE_ID_COLUMN)));
                assessment.setAssessmentName(cursor.getString(cursor.getColumnIndex(DBHelper.ASSESSMENT_NAME_COLUMN)));
                assessment.setAssessmentGoalDate(cursor.getString(cursor.getColumnIndex(DBHelper.ASSESSMENT_GOAL_DATE_COLUMN)));
                assessment.setAssessmentType(cursor.getString(cursor.getColumnIndex(DBHelper.ASSESSMENT_TYPE_COLUMN)));
                assessmentList.add(assessment);
            }
        }
    }


}
