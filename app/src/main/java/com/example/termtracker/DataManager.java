package com.example.termtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager extends SQLiteOpenHelper {

    // Constants for db name and version
    private static final String DATABASE_NAME = "termTracker.db";
    private static final int DATABASE_VERSION = 1;

    // table names
    public static final String TABLE_TERMS = "terms";
    public static final String TABLE_COURSES = "courses";
    public static final String TABLE_ASSESS = "assess";
    public static final String TERM_CREATED = "termCreated";

    // common column names
    public static final String TERM_ID = "_id";
    public static final String COURSE_ID = "courseID";

    // table terms columns
    public static final String TERM_TITLE = "termTitle";
    /*public static final String TSTART_DATE = "tStartDate";
    public static final String END_DATE = "endDate";*/

    // table courses columns
    public static final String COURSE_TITLE = "courseTitle";
    public static final String CSTART_DATE = "cStartDate";
    public static final String ANT_END_DATE = "antEndDate";
    public static final String STATUS = "status";
    public static final String MENTOR_NAME = "mentorName";
    public static final String M_PHONE_NUMBER = "mPhoneNumber";
    public static final String M_EMAIL = "mEmail";

    // table assess columns
    public static final String ASSESSMENT_ID = "assessmentID";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String DUE_DATE = "dueDate";
    public static final String NOTES = "notes";

    public static final String[] ALL_TERM_COLUMNS = {TERM_ID, TERM_TITLE, TERM_CREATED };

    // SQL to create tables
    private static final String CREATE_TABLE_TERMS =
            "CREATE TABLE " + TABLE_TERMS + " (" +
                    TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_TITLE + " TEXT," +
                    TERM_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    /*private static final String CREATE_TABLE_COURSES =
            "CREATE TABLE " + TABLE_COURSES + " (" +
                    COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_TITLE + " TEXT, " +
                    CSTART_DATE + " TEXT, " +
                    ANT_END_DATE + " TEXT, " +
                    STATUS + " CHAR, " +
                    MENTOR_NAME + " TEXT, " +
                    M_PHONE_NUMBER + " TEXT, " +
                    M_EMAIL + " TEXT, " +
                    TERM_ID + " INTEGER REFERENCES TABLE_TERMS" +
                    ")";*/

    /*private static final String CREATE_TABLE_ASSESS =
            "CREATE TABLE " + TABLE_ASSESS + " (" +
                    ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    DUE_DATE + " TEXT, " +
                    NOTES + " TEXT, " +
                    COURSE_ID + " INTEGER REFERENCES TABLE_COURSES " +
                    ")";*/

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TERMS);
        //sqLiteDatabase.execSQL(CREATE_TABLE_COURSES);
        //sqLiteDatabase.execSQL(CREATE_TABLE_ASSESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_COURSES);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_ASSESS);
        onCreate(sqLiteDatabase);
    }
}