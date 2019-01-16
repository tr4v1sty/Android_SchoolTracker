package com.travis.c196_project.Data;

import android.content.ContentValues;
import android.content.Context;
import com.travis.c196_project.Models.Assessment;
import com.travis.c196_project.Utilities.DBHelper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.*;

public class AssessmentData {

    public static final String ASSESSMENTS_TABLE = "assessments";
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public AssessmentData(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

    public static final String ASSESSMENT_NOTIFICATION_COLUMN = "assessmentNotification";
    public static final String ASSESSMENT_GOAL_DATE_COLUMN = "assessmentGoalDate";
    public static final String ASSESSMENT_TYPE_COLUMN = "assessmentType";
    public static final String ASSESSMENT_NAME_COLUMN = "assessmentName";
    public static final String ASSESSMENT_COURSE_ID_COLUMN = "assessmentCourseId";
    public static final String ASSESSMENT_TABLE_ID_COLUMN = "assessmentId";

    private static final String [] columns_assessments = {
            ASSESSMENT_COURSE_ID_COLUMN,
            ASSESSMENT_GOAL_DATE_COLUMN,
            ASSESSMENT_NAME_COLUMN,
            ASSESSMENT_NOTIFICATION_COLUMN,
            ASSESSMENT_TABLE_ID_COLUMN,
            ASSESSMENT_TYPE_COLUMN,
    };

    public Assessment createAssessment(Assessment assessment) {

        ContentValues values = new ContentValues();

        values.put(ASSESSMENT_COURSE_ID_COLUMN,
                assessment.getCourseId());
        values.put(ASSESSMENT_NAME_COLUMN,
                assessment.getAssessmentName());
        values.put(ASSESSMENT_TYPE_COLUMN,
                assessment.getAssessmentType());
        values.put(ASSESSMENT_NOTIFICATION_COLUMN,
                assessment.getAssessmentNotification());
        values.put(ASSESSMENT_GOAL_DATE_COLUMN,
                assessment.getAssessmentGoalDate());

        long insertId = database.insert(ASSESSMENTS_TABLE,
                null,
                values);

        assessment.setAssessmentId(insertId);

        return assessment;
    }

    public void deleteAssessment(long id) {

        database.delete(ASSESSMENTS_TABLE,
                ASSESSMENT_TABLE_ID_COLUMN
                        + " = " + id,
                null);
    }

    public void updateAssessment(Assessment assessment) {

        ContentValues values = new ContentValues();

        values.put(ASSESSMENT_TABLE_ID_COLUMN,
                assessment.getAssessmentId());
        values.put(ASSESSMENT_NAME_COLUMN,
                assessment.getAssessmentName());
        values.put(ASSESSMENT_TYPE_COLUMN,
                assessment.getAssessmentType());
        values.put(ASSESSMENT_NOTIFICATION_COLUMN,
                assessment.getAssessmentNotification());
        values.put(ASSESSMENT_GOAL_DATE_COLUMN,
                assessment.getAssessmentGoalDate());

        database.update(ASSESSMENTS_TABLE,
                values,
                ASSESSMENT_TABLE_ID_COLUMN + "=" + assessment.getAssessmentId(),
                null);
    }

    public List<Assessment> getAssessments(long courseId) {

        List<Assessment> assessmentList = new ArrayList<>();

        String[] selectionArgs = new String[]{Long.toString(courseId)};

        Cursor cursor;
        cursor = database.query(ASSESSMENTS_TABLE,
                columns_assessments,
                ASSESSMENT_COURSE_ID_COLUMN + " = ?",
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

                assessment.setAssessmentId(cursor.getLong(cursor.getColumnIndex(ASSESSMENT_TABLE_ID_COLUMN)));
                assessment.setCourseId(cursor.getLong(cursor.getColumnIndex(ASSESSMENT_COURSE_ID_COLUMN)));

                assessment.setAssessmentName(cursor.getString(cursor.getColumnIndex(ASSESSMENT_NAME_COLUMN)));
                assessment.setAssessmentGoalDate(cursor.getString(cursor.getColumnIndex(ASSESSMENT_GOAL_DATE_COLUMN)));
                assessment.setAssessmentType(cursor.getString(cursor.getColumnIndex(ASSESSMENT_TYPE_COLUMN)));

                assessmentList.add(assessment);
            }
        }
    }
}
