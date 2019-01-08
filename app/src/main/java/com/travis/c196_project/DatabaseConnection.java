package com.travis.c196_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private SQLiteDatabase database;
    private SQLiteOpenHelper databaseHelper;


    private static final String [] columns_terms = {
            DBHelper.TERM_END_COLUMN,
            DBHelper.TERM_ID_COLUMN,
            DBHelper.TERM_NAME_COLUMN,
            DBHelper.TERM_START_COLUMN
    };

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
//            DBHelper.COURSE_MENTOR_EMAIL_TWO_COLUMN,
            DBHelper.COURSE_MENTOR_ONE_COLUMN,
//            DBHelper.COURSE_MENTOR_TWO_COLUMN,
            DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN,
//            DBHelper.COURSE_MENTOR_PHONE_TWO_COLUMN
    };

    private static final String [] columns_assessments = {
            DBHelper.ASSESSMENT_COURSE_ID_COLUMN,
            DBHelper.ASSESSMENT_GOAL_DATE_COLUMN,
            DBHelper.ASSESSMENT_NAME_COLUMN,
            DBHelper.ASSESSMENT_NOTIFICATION_COLUMN,
            DBHelper.ASSESSMENT_TABLE_ID_COLUMN,
            DBHelper.ASSESSMENT_TYPE_COLUMN,

    };

    public DatabaseConnection (Context context) {
        databaseHelper = new DBHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public Term createTerm (Term term){
        ContentValues values = new ContentValues();
        values.put(DBHelper.TERM_NAME_COLUMN, term.getTermName());
        values.put(DBHelper.TERM_START_COLUMN, term.getTermStart());
        values.put(DBHelper.TERM_END_COLUMN, term.getTermEnd());

        long insertId = database.insert(DBHelper.TERM_TABLE, null, values);
        term.setTermId(insertId);

        return term;
    }

    public Course createCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COURSE_TERM_ID_COLUMN, course.getCourseTermId());
        values.put(DBHelper.COURSE_NAME_COLUMN, course.getCourseName());
        values.put(DBHelper.COURSE_START_COLUMN, course.getCourseStart());
        values.put(DBHelper.COURSE_END_COLUMN, course.getCourseEnd());
        values.put(DBHelper.COURSE_STATUS_COLUMN, course.getCourseStatus());
        values.put(DBHelper.COURSE_MENTOR_ONE_COLUMN, course.getCourseMentorName());
//        values.put(DatabaseHelper.COURSE_MENTOR_TWO, course.getCourseMentorTwo());
        values.put(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN, course.getCourseMentorPhone());
//        values.put(DatabaseHelper.COURSE_MENTOR_PHONE_TWO, course.getCourseMentorPhoneTwo());
        values.put(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN, course.getCourseMentorEmail());
//        values.put(DatabaseHelper.COURSE_MENTOR_EMAIL_TWO, course.getCourseMentorEmailTwo());
        values.put(DBHelper.COURSE_NOTIFICATION_START_COLUMN, course.getCourseNotificationStart());
        values.put(DBHelper.COURSE_NOTIFICATION_END_COLUMN, course.getCourseNotificationEnd());
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN, course.getCourseNotesTitle());
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN, course.getCourseNotesText());

        long insertId = database.insert(DBHelper.COURSES_TABLE, null, values);
        course.setCourseId(insertId);
        return course;
    }

    public Assessment createAssessment(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ASSESSMENT_COURSE_ID_COLUMN, assessment.getCourseId());
        values.put(DBHelper.ASSESSMENT_NAME_COLUMN, assessment.getAssessmentName());
        values.put(DBHelper.ASSESSMENT_TYPE_COLUMN, assessment.getAssessmentType());
        values.put(DBHelper.ASSESSMENT_NOTIFICATION_COLUMN, assessment.getAssessmentNotification());
        values.put(DBHelper.ASSESSMENT_GOAL_DATE_COLUMN, assessment.getAssessmentGoalDate());

        long insertId = database.insert(DBHelper.ASSESSMENTS_TABLE, null, values);
        assessment.setAssessmentId(insertId);
        return assessment;
    }

    public void updateTerm(Term term) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TERM_ID_COLUMN, term.getTermId());
        values.put(DBHelper.TERM_NAME_COLUMN, term.getTermName());
        values.put(DBHelper.TERM_START_COLUMN, term.getTermStart());
        values.put(DBHelper.TERM_END_COLUMN, term.getTermEnd());
        database.update(DBHelper.TERM_TABLE, values, DBHelper.TERM_ID_COLUMN + "=" + term.getTermId(), null);
    }

    public void updateCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COURSE_ID_COLUMN, course.getCourseId());
        values.put(DBHelper.COURSE_NAME_COLUMN, course.getCourseName());
        values.put(DBHelper.COURSE_START_COLUMN, course.getCourseStart());
        values.put(DBHelper.COURSE_END_COLUMN, course.getCourseEnd());
        values.put(DBHelper.COURSE_STATUS_COLUMN, course.getCourseStatus());
        values.put(DBHelper.COURSE_MENTOR_ONE_COLUMN, course.getCourseMentorName());
//        values.put(DatabaseHelper.COURSE_MENTOR_TWO, course.getCourseMentorTwo());
        values.put(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN, course.getCourseMentorPhone());
//        values.put(DatabaseHelper.COURSE_MENTOR_PHONE_TWO, course.getCourseMentorPhoneTwo());
        values.put(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN, course.getCourseMentorEmail());
//        values.put(DatabaseHelper.COURSE_MENTOR_EMAIL_TWO, course.getCourseMentorEmailTwo());
        values.put(DBHelper.COURSE_NOTIFICATION_START_COLUMN, course.getCourseNotificationStart());
        values.put(DBHelper.COURSE_NOTIFICATION_END_COLUMN, course.getCourseNotificationEnd());
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN, course.getCourseNotesTitle());
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN, course.getCourseNotesText());
        database.update(DBHelper.COURSES_TABLE, values, DBHelper.COURSE_ID_COLUMN + "=" + course.getCourseId(), null);
    }

    public void updateAssessment(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ASSESSMENT_TABLE_ID_COLUMN, assessment.getAssessmentId());
        values.put(DBHelper.ASSESSMENT_NAME_COLUMN, assessment.getAssessmentName());
        values.put(DBHelper.ASSESSMENT_TYPE_COLUMN, assessment.getAssessmentType());
        values.put(DBHelper.ASSESSMENT_NOTIFICATION_COLUMN, assessment.getAssessmentNotification());
        values.put(DBHelper.ASSESSMENT_GOAL_DATE_COLUMN, assessment.getAssessmentGoalDate());
        database.update(DBHelper.ASSESSMENTS_TABLE, values, DBHelper.ASSESSMENT_TABLE_ID_COLUMN + "=" + assessment.getAssessmentId(), null);
    }

    public void updateNotes(long id, String notesName, String notesBody) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COURSE_ID_COLUMN, id);
        values.put(DBHelper.COURSE_NOTES_TITLE_COLUMN, notesName);
        values.put(DBHelper.COURSE_NOTES_TEXT_COLUMN, notesBody);
        database.update(DBHelper.COURSES_TABLE, values, DBHelper.COURSE_ID_COLUMN + "=" + id, null);
    }

    public void deleteTerm(long id) {
        System.out.println("Term with id: " + id + " deleted");
        database.delete(DBHelper.TERM_TABLE, DBHelper.TERM_ID_COLUMN
                + " = " + id, null);
    }

    public void deleteCourse(long id) {
        System.out.println("Course with id: " + id + " deleted");
        database.delete(DBHelper.COURSES_TABLE, DBHelper.COURSE_ID_COLUMN
                + " = " + id, null);
    }

    public void deleteAssessment(long id) {
//        long id = assessment.getAssessmentId();
        System.out.println("Assessment with id: " + id + " deleted");
        database.delete(DBHelper.ASSESSMENTS_TABLE, DBHelper.ASSESSMENT_TABLE_ID_COLUMN
                + " = " + id, null);
    }

    public List<Term> getAllTerms() {
        List<Term> termList = new ArrayList<Term>();

        Cursor cursor = database.query(DBHelper.TERM_TABLE,
                columns_terms, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Term term = new Term();
                term.setTermId(cursor.getLong(cursor.getColumnIndex(DBHelper.TERM_ID_COLUMN)));
                term.setTermName(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_NAME_COLUMN)));
                term.setTermStart(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_START_COLUMN)));
                term.setTermEnd(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_END_COLUMN)));
                termList.add(term);
            }
        }
        return termList;
    }

    public List<Course> getCourses(long termId) {
        List<Course> courseList = new ArrayList<>();
        String[] selectionArgs = {Long.toString(termId)};
        Cursor cursor = database.query(DBHelper.COURSES_TABLE,
                columns_courses, DBHelper.COURSE_TERM_ID_COLUMN + " = ?", selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Course course = new Course();
                course.setCourseId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID_COLUMN)));
                course.setTermId(cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_TERM_ID_COLUMN)));
                course.setCourseName(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NAME_COLUMN)));
                course.setCourseStart(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_START_COLUMN)));
                course.setCourseEnd(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_END_COLUMN)));
                course.setCourseStatus(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_STATUS_COLUMN)));
                course.setCourseMentorName(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_ONE_COLUMN)));
//                course.setCourseMentorTwo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COURSE_MENTOR_TWO)));
                course.setCourseMentorPhone(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_PHONE_ONE_COLUMN)));
//                course.setCourseMentorPhoneTwo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COURSE_MENTOR_PHONE_TWO)));
                course.setCourseMentorEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_MENTOR_EMAIL_ONE_COLUMN)));
//                course.setCourseMentorEmailTwo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COURSE_MENTOR_EMAIL_TWO)));
                course.setCourseNotificationStart(cursor.getInt(cursor.getColumnIndex(DBHelper.COURSE_NOTIFICATION_START_COLUMN)));
                course.setCourseNotificationEnd(cursor.getInt(cursor.getColumnIndex(DBHelper.COURSE_NOTIFICATION_END_COLUMN)));
                course.setCourseNotesTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TITLE_COLUMN)));
                course.setCourseNotesText(cursor.getString(cursor.getColumnIndex(DBHelper.COURSE_NOTES_TEXT_COLUMN)));
                courseList.add(course);
            }
        }
        return courseList;
    }

    public List<Assessment> getAssessments(long courseId) {
        List<Assessment> assessmentList = new ArrayList<>();
        String[] selectionArgs = {Long.toString(courseId)};
        Cursor cursor = database.query(DBHelper.ASSESSMENTS_TABLE,
                columns_assessments, DBHelper.ASSESSMENT_COURSE_ID_COLUMN + " = ?", selectionArgs, null, null, null);

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
        return assessmentList;
    }

    public Course getNotes(long courseId) {
//        List<CourseModel> notesList = new ArrayList<CourseModel>();
        Course course = new Course();
        String[] selectionArgs = {Long.toString(courseId)};
        Cursor cursor = database.query(DBHelper.COURSES_TABLE,
                columns_courses, DBHelper.COURSE_ID_COLUMN + " = ?", selectionArgs, null, null, null);

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
