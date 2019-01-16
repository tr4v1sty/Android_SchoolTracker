package com.travis.c196_project.Data;

import android.content.ContentValues;
import android.content.Context;
import com.travis.c196_project.Models.Course;
import com.travis.c196_project.Utilities.DBHelper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class CourseData {

    public static final String COURSES_TABLE = "courses";
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public CourseData (Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

    public static final String COURSE_NOTES_TEXT_COLUMN = "courseNotesText";
    public static final String COURSE_NOTIFICATION_END_COLUMN = "courseNotificationEnd";
    public static final String COURSE_NOTIFICATION_START_COLUMN = "courseNotificationStart";
    public static final String COURSE_MENTOR_EMAIL_COLUMN = "courseMentorEmailOne";
    public static final String COURSE_MENTOR_PHONE_COLUMN = "courseMentorPhoneOne";
    public static final String COURSE_MENTOR_COLUMN = "courseMentorOne";
    public static final String COURSE_STATUS_COLUMN = "courseStatus";
    public static final String COURSE_END_COLUMN = "courseEnd";
    public static final String COURSE_START_COLUMN = "courseStart";
    public static final String COURSE_NAME_COLUMN = "courseName";
    public static final String COURSE_TERM_ID_COLUMN = "courseTermId";
    public static final String COURSE_ID_COLUMN = "courseId";

    private static final String [] columns_courses = {

            COURSE_TERM_ID_COLUMN,
            COURSE_ID_COLUMN,

            COURSE_NAME_COLUMN,
            COURSE_STATUS_COLUMN,

            COURSE_START_COLUMN,
            COURSE_END_COLUMN,

            COURSE_NOTES_TEXT_COLUMN,

            COURSE_NOTIFICATION_END_COLUMN,
            COURSE_NOTIFICATION_START_COLUMN,

            COURSE_MENTOR_EMAIL_COLUMN,
            COURSE_MENTOR_COLUMN,
            COURSE_MENTOR_PHONE_COLUMN,
    };

    public Course createCourse(Course course) {

        ContentValues values = new ContentValues();

        values.put(COURSE_TERM_ID_COLUMN,
                course.getCourseTermId());
        values.put(COURSE_NAME_COLUMN,
                course.getCourseName());
        values.put(COURSE_START_COLUMN,
                course.getCourseStart());
        values.put(COURSE_END_COLUMN,
                course.getCourseEnd());
        values.put(COURSE_STATUS_COLUMN,
                course.getCourseStatus());
        values.put(COURSE_MENTOR_COLUMN,
                course.getCourseMentorName());
        values.put(COURSE_MENTOR_PHONE_COLUMN,
                course.getCourseMentorPhone());
        values.put(COURSE_MENTOR_EMAIL_COLUMN,
                course.getCourseMentorEmail());
        values.put(COURSE_NOTIFICATION_START_COLUMN,
                course.getNotificationStartDate());
        values.put(COURSE_NOTIFICATION_END_COLUMN,
                course.getNotificationEndDate());
        values.put(COURSE_NOTES_TEXT_COLUMN,
                course.getCourseNotesText());

        long insertId = database.insert(COURSES_TABLE, null, values);

        course.setCourseId(insertId);

        return course;
    }

    public void deleteCourse(long id) {

        database.delete(COURSES_TABLE,
                COURSE_ID_COLUMN
                        + " = " + id,
                null);
    }

    public void updateCourse(Course course) {

        ContentValues values = new ContentValues();

        values.put(COURSE_ID_COLUMN,
                course.getCourseId());
        values.put(COURSE_NAME_COLUMN,
                course.getCourseName());
        values.put(COURSE_START_COLUMN,
                course.getCourseStart());
        values.put(COURSE_END_COLUMN,
                course.getCourseEnd());
        values.put(COURSE_STATUS_COLUMN,
                course.getCourseStatus());
        values.put(COURSE_MENTOR_COLUMN,
                course.getCourseMentorName());
        values.put(COURSE_MENTOR_PHONE_COLUMN,
                course.getCourseMentorPhone());
        values.put(COURSE_MENTOR_EMAIL_COLUMN,
                course.getCourseMentorEmail());
        values.put(COURSE_NOTIFICATION_START_COLUMN,
                course.getNotificationStartDate());
        values.put(COURSE_NOTIFICATION_END_COLUMN,
                course.getNotificationEndDate());
        values.put(COURSE_NOTES_TEXT_COLUMN,
                course.getCourseNotesText());

        database.update(COURSES_TABLE,
                values,
                COURSE_ID_COLUMN + "=" + course.getCourseId(),
                null);
    }

    public List<Course> getCourses(long termId) {

        List<Course> courseList = new ArrayList<>();

        String[] selectionArgs = new String[]{Long.toString(termId)};

        Cursor cursor;
        cursor = database.query(COURSES_TABLE,
                columns_courses,
                COURSE_TERM_ID_COLUMN + " = ?",
                selectionArgs,
                null,
                null,
                null);

        cursorGetCourse(courseList, cursor);

        return courseList;
    }

    private void cursorGetCourse(List<Course> courseList, Cursor cursor) {

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Course course = new Course();

                course.setCourseId(cursor.getLong(cursor.getColumnIndex(COURSE_ID_COLUMN)));
                course.setTermId(cursor.getLong(cursor.getColumnIndex(COURSE_TERM_ID_COLUMN)));

                course.setCourseName(cursor.getString(cursor.getColumnIndex(COURSE_NAME_COLUMN)));
                course.setCourseStart(cursor.getString(cursor.getColumnIndex(COURSE_START_COLUMN)));
                course.setCourseEnd(cursor.getString(cursor.getColumnIndex(COURSE_END_COLUMN)));
                course.setCourseStatus(cursor.getString(cursor.getColumnIndex(COURSE_STATUS_COLUMN)));

                course.setCourseMentorName(cursor.getString(cursor.getColumnIndex(COURSE_MENTOR_COLUMN)));
                course.setCourseMentorPhone(cursor.getString(cursor.getColumnIndex(COURSE_MENTOR_PHONE_COLUMN)));
                course.setCourseMentorEmail(cursor.getString(cursor.getColumnIndex(COURSE_MENTOR_EMAIL_COLUMN)));

                course.setNotificationStartDate(cursor.getInt(cursor.getColumnIndex(COURSE_NOTIFICATION_START_COLUMN)));
                course.setNotificationEndDate(cursor.getInt(cursor.getColumnIndex(COURSE_NOTIFICATION_END_COLUMN)));

                course.setCourseNotesText(cursor.getString(cursor.getColumnIndex(COURSE_NOTES_TEXT_COLUMN)));

                courseList.add(course);
            }
        }
    }

    public Course getNotes(long courseId) {

        Course course = new Course();

        String[] selectionArgs = new String[]{Long.toString(courseId)};

        Cursor cursor;
        cursor = database.query(COURSES_TABLE,
                columns_courses,
                COURSE_ID_COLUMN + " = ?",
                selectionArgs,
                null,
                null,
                null);

        cursorGetNote(course, cursor);

        return course;
    }

    public void updateNotes(long id, String notesBody) {

        ContentValues values = new ContentValues();

        values.put(COURSE_ID_COLUMN,
                id);
        values.put(COURSE_NOTES_TEXT_COLUMN,
                notesBody);

        database.update(COURSES_TABLE,
                values,
                COURSE_ID_COLUMN + "=" + id,
                null);
    }

    private void cursorGetNote(Course course, Cursor cursor) {

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                course.setCourseId(cursor.getLong(cursor.getColumnIndex(COURSE_ID_COLUMN)));
                course.setTermId(cursor.getLong(cursor.getColumnIndex(COURSE_TERM_ID_COLUMN)));

                course.setCourseNotesText(cursor.getString(cursor.getColumnIndex(COURSE_NOTES_TEXT_COLUMN)));

            }
        }
    }
}
