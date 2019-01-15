package com.travis.c196_project.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travis.c196_project.Data.AssessmentData;
import com.travis.c196_project.Data.CourseData;
import com.travis.c196_project.Data.TermData;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SchoolInfo.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATION_STRING_FOR_TERMS_TABLE);
        db.execSQL(CREATION_STRING_FOR_COURSES_TABLE);
        db.execSQL(CREATION_STRING_FOR_ASSESSMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TermData.TERM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CourseData.COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AssessmentData.ASSESSMENTS_TABLE);
        onCreate(db);
    }

    private static final String CREATION_STRING_FOR_TERMS_TABLE = "CREATE TABLE " +
            TermData.TERM_TABLE + " (" +
                    TermData.TERM_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TermData.TERM_NAME_COLUMN + " TEXT, " +
                    TermData.TERM_START_COLUMN + " DATE, " +
                    TermData.TERM_END_COLUMN + " DATE " +
                    ")";

    private static final String CREATION_STRING_FOR_COURSES_TABLE = "CREATE TABLE " +
            CourseData.COURSES_TABLE + " (" +
                    CourseData.COURSE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CourseData.COURSE_TERM_ID_COLUMN + " INTEGER, " +
                    CourseData.COURSE_NAME_COLUMN + " TEXT, " +
                    CourseData.COURSE_START_COLUMN + " DATE, " +
                    CourseData.COURSE_END_COLUMN + " DATE, " +
                    CourseData.COURSE_STATUS_COLUMN + " TEXT, " +
                    CourseData.COURSE_MENTOR_COLUMN + " TEXT, " +
                    CourseData.COURSE_MENTOR_PHONE_COLUMN + " TEXT, " +
                    CourseData.COURSE_MENTOR_EMAIL_COLUMN + " TEXT, " +
                    CourseData.COURSE_NOTIFICATION_START_COLUMN + " INTEGER, " +
                    CourseData.COURSE_NOTIFICATION_END_COLUMN + " INTEGER, " +
                    CourseData.COURSE_NOTES_TITLE_COLUMN + " TEXT, " +
                    CourseData.COURSE_NOTES_TEXT_COLUMN + " TEXT, " +

                    "FOREIGN KEY(" + CourseData.COURSE_TERM_ID_COLUMN +
                    ") REFERENCES " + TermData.TERM_TABLE + "(" + TermData.TERM_ID_COLUMN + "))";

    private static final String CREATION_STRING_FOR_ASSESSMENTS_TABLE = "CREATE TABLE " +
            AssessmentData.ASSESSMENTS_TABLE + " (" +
                    AssessmentData.ASSESSMENT_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AssessmentData.ASSESSMENT_COURSE_ID_COLUMN + " INTEGER, " +
                    AssessmentData.ASSESSMENT_NAME_COLUMN + " TEXT, " +
                    AssessmentData.ASSESSMENT_TYPE_COLUMN + " TEXT, " +
                    AssessmentData.ASSESSMENT_GOAL_DATE_COLUMN + " TEXT, " +
                    AssessmentData.ASSESSMENT_NOTIFICATION_COLUMN + " INTEGER, " +

                    "FOREIGN KEY(" + AssessmentData.ASSESSMENT_COURSE_ID_COLUMN +
                    ") REFERENCES " + CourseData.COURSES_TABLE + "(" + CourseData.COURSE_ID_COLUMN + "))";
}
