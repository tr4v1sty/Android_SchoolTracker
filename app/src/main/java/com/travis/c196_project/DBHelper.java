package com.travis.c196_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //Database name and version
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
        db.execSQL("DROP TABLE IF EXISTS " + TERM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSESSMENTS_TABLE);
        onCreate(db);
    }

    //Variables for each table and its associated columns.
    // Table: Terms
    public static final String TERM_TABLE = "terms";
    public static final String TERM_ID_COLUMN = "termId";
    public static final String TERM_NAME_COLUMN = "termName";
    public static final String TERM_START_COLUMN = "termStart";
    public static final String TERM_END_COLUMN = "termEnd";

    // Set the name and type with addition of other attributes such as
    // auto increment and the proper key associations.
    private static final String CREATION_STRING_FOR_TERMS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TERM_TABLE + " (" +
                    TERM_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_NAME_COLUMN + " TEXT, " +
                    TERM_START_COLUMN + " DATE, " +
                    TERM_END_COLUMN + " DATE " +
                    ")";

    // Table: Courses
    public static final String COURSES_TABLE = "courses";
    public static final String COURSE_ID_COLUMN = "courseId";
    public static final String COURSE_TERM_ID_COLUMN = "courseTermId";
    public static final String COURSE_NAME_COLUMN = "courseName";
    public static final String COURSE_START_COLUMN = "courseStart";
    public static final String COURSE_END_COLUMN = "courseEnd";
    public static final String COURSE_STATUS_COLUMN = "courseStatus";
    public static final String COURSE_MENTOR_ONE_COLUMN = "courseMentorOne";
    public static final String COURSE_MENTOR_PHONE_ONE_COLUMN = "courseMentorPhoneOne";
    public static final String COURSE_MENTOR_EMAIL_ONE_COLUMN = "courseMentorEmailOne";
    public static final String COURSE_NOTIFICATION_START_COLUMN = "courseNotificationStart";
    public static final String COURSE_NOTIFICATION_END_COLUMN = "courseNotificationEnd";
    public static final String COURSE_NOTES_TITLE_COLUMN = "courseNotesTitle";
    public static final String COURSE_NOTES_TEXT_COLUMN = "courseNotesText";

    private static final String CREATION_STRING_FOR_COURSES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + COURSES_TABLE + " (" +
                    COURSE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_TERM_ID_COLUMN + " INTEGER, " +
                    COURSE_NAME_COLUMN + " TEXT, " +
                    COURSE_START_COLUMN + " DATE, " +
                    COURSE_END_COLUMN + " DATE, " +
                    COURSE_STATUS_COLUMN + " TEXT, " +
                    COURSE_MENTOR_ONE_COLUMN + " TEXT, " +
                    COURSE_MENTOR_PHONE_ONE_COLUMN + " TEXT, " +
                    COURSE_MENTOR_EMAIL_ONE_COLUMN + " TEXT, " +
                    COURSE_NOTIFICATION_START_COLUMN + " INTEGER, " +
                    COURSE_NOTIFICATION_END_COLUMN + " INTEGER, " +
                    COURSE_NOTES_TITLE_COLUMN + " TEXT, " +
                    COURSE_NOTES_TEXT_COLUMN + " TEXT, " +

                    "FOREIGN KEY(" + COURSE_TERM_ID_COLUMN + ") REFERENCES " + TERM_TABLE + "(" + TERM_ID_COLUMN + ")" +
                    "ON DELETE RESTRICT)";

    // Table: Assessment
    public static final String ASSESSMENTS_TABLE = "assessments";
    public static final String ASSESSMENT_TABLE_ID_COLUMN = "assessmentId";
    public static final String ASSESSMENT_COURSE_ID_COLUMN = "assessmentCourseId";
    public static final String ASSESSMENT_NAME_COLUMN = "assessmentName";
    public static final String ASSESSMENT_TYPE_COLUMN = "assessmentType";
    public static final String ASSESSMENT_GOAL_DATE_COLUMN = "assessmentGoalDate";
    public static final String ASSESSMENT_NOTIFICATION_COLUMN = "assessmentNotification";

    private static final String CREATION_STRING_FOR_ASSESSMENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ASSESSMENTS_TABLE + " (" +
                    ASSESSMENT_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_COURSE_ID_COLUMN + " INTEGER, " +
                    ASSESSMENT_NAME_COLUMN + " TEXT, " +
                    ASSESSMENT_TYPE_COLUMN + " TEXT, " +
                    ASSESSMENT_GOAL_DATE_COLUMN + " TEXT, " +
                    ASSESSMENT_NOTIFICATION_COLUMN + " INTEGER, " +
                    "FOREIGN KEY(" + ASSESSMENT_COURSE_ID_COLUMN + ") REFERENCES " + COURSES_TABLE + "(" + COURSE_ID_COLUMN + ")" +
                    ")";



}
