package com.travis.c196_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CourseData {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public CourseData (Context context) {

        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

    private static final String [] columns_courses = {

            DBHelper.COURSE_TERM_ID_COLUMN,
            DBHelper.COURSE_ID_COLUMN,

            DBHelper.COURSE_NAME_COLUMN,
            DBHelper.COURSE_STATUS_COLUMN,

            DBHelper.COURSE_START_COLUMN,
            DBHelper.COURSE_END_COLUMN,

            DBHelper.COURSE_NOTES_TEXT_COLUMN,
            DBHelper.COURSE_NOTES_TITLE_COLUMN,

            DBHelper.COURSE_NOTIFICATION_END_COLUMN,
            DBHelper.COURSE_NOTIFICATION_START_COLUMN,

            DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN,
            DBHelper.COURSE_MENTOR_ONE_COLUMN,
            DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN,
    };

    Course createCourse(Course course) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.COURSE_TERM_ID_COLUMN,
                course.getCourseTermId());
        values.put(DBHelper.COURSE_NAME_COLUMN,
                course.getCourseName());
        values.put(DBHelper.COURSE_START_COLUMN,
                course.getCourseStart());
        values.put(DBHelper.COURSE_END_COLUMN,
                course.getCourseEnd());
        values.put(DBHelper.COURSE_STATUS_COLUMN,
                course.getCourseStatus());
        values.put(DBHelper.COURSE_MENTOR_ONE_COLUMN,
                course.getCourseMentorName());
        values.put(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN,
                course.getCourseMentorPhone());
        values.put(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN,
                course.getCourseMentorEmail());
        values.put(DBHelper.COURSE_NOTIFICATION_START_COLUMN,
                course.getCourseNotificationStart());
        values.put(DBHelper.COURSE_NOTIFICATION_END_COLUMN,
                course.getCourseNotificationEnd());
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN,
                course.getCourseNotesTitle());
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN,
                course.getCourseNotesText());

        long insertId;
        insertId = database.insert(DBHelper.COURSES_TABLE, null, values);

        course.setCourseId(insertId);

        return course;
    }


    void updateCourse(Course course) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.COURSE_ID_COLUMN,
                course.getCourseId());
        values.put(DBHelper.COURSE_NAME_COLUMN,
                course.getCourseName());
        values.put(DBHelper.COURSE_START_COLUMN,
                course.getCourseStart());
        values.put(DBHelper.COURSE_END_COLUMN,
                course.getCourseEnd());
        values.put(DBHelper.COURSE_STATUS_COLUMN,
                course.getCourseStatus());
        values.put(DBHelper.COURSE_MENTOR_ONE_COLUMN,
                course.getCourseMentorName());
        values.put(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN,
                course.getCourseMentorPhone());
        values.put(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN,
                course.getCourseMentorEmail());
        values.put(DBHelper.COURSE_NOTIFICATION_START_COLUMN,
                course.getCourseNotificationStart());
        values.put(DBHelper.COURSE_NOTIFICATION_END_COLUMN,
                course.getCourseNotificationEnd());
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN,
                course.getCourseNotesTitle());
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN,
                course.getCourseNotesText());

        database.update(DBHelper.COURSES_TABLE,
                values,
                DBHelper.COURSE_ID_COLUMN + "=" + course.getCourseId(),
                null);
    }

    void updateNotes(long id, String notesName, String notesBody) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.COURSE_ID_COLUMN,
                id);
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN,
                notesName);
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN,
                notesBody);

        database.update(DBHelper.COURSES_TABLE,
                values,
                DBHelper.COURSE_ID_COLUMN + "=" + id,
                null);
    }

    public void deleteCourse(long id) {

        database.delete(DBHelper.COURSES_TABLE,
                DBHelper.COURSE_ID_COLUMN
                        + " = " + id,
                null);
    }

    List<Course> getCourses(long termId) {

        List<Course> courseList = new ArrayList<>();

        String[] selectionArgs = new String[]{Long.toString(termId)};

        Cursor cursor;
        cursor = database.query(DBHelper.COURSES_TABLE,
                columns_courses,
                DBHelper.COURSE_TERM_ID_COLUMN + " = ?",
                selectionArgs,
                null,
                null,
                null);

        if (cursor.getCount() > 0) while (cursor.moveToNext()) {
            Course course = new Course();
            course.setCourseId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID_COLUMN)));
            course.setTermId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_TERM_ID_COLUMN)));
            course.setCourseName(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NAME_COLUMN)));
            course.setCourseStart(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_START_COLUMN)));
            course.setCourseEnd(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_END_COLUMN)));
            course.setCourseStatus(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_STATUS_COLUMN)));
            course.setCourseMentorName(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_ONE_COLUMN)));
            course.setCourseMentorPhone(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN)));
            course.setCourseMentorEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN)));
            course.setCourseNotificationStart(cursor.getInt(cursor.getColumnIndex(DBHelper.COURSE_NOTIFICATION_START_COLUMN)));
            course.setCourseNotificationEnd(cursor.getInt(cursor.getColumnIndex(DBHelper.COURSE_NOTIFICATION_END_COLUMN)));
            course.setCourseNotesTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TITLE_COLUMN)));
            course.setCourseNotesText(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TEXT_COLUMN)));
            courseList.add(course);
        }

        return courseList;
    }

    Course getNotes(long courseId) {

        Course course = new Course();

        String[] selectionArgs = new String[]{Long.toString(courseId)};

        Cursor cursor;
        cursor = database.query(DBHelper.COURSES_TABLE,
                columns_courses,
                DBHelper.COURSE_ID_COLUMN + " = ?",
                selectionArgs,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                course.setCourseId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID_COLUMN)));
                course.setTermId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_TERM_ID_COLUMN)));
                course.setCourseNotesTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TITLE_COLUMN)));
                course.setCourseNotesText(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TEXT_COLUMN)));
            }
        }
        return course;
    }

}
