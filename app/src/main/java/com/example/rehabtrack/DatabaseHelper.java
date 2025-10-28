package com.example.rehabtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // --- Database Constants ---
    // This makes it easy to change the database version later
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rehabtrack.db";

    // --- Table 1: Exercises ---
    private static final String TABLE_EXERCISES = "exercises";
    private static final String KEY_EX_ID = "id";
    private static final String KEY_EX_NAME = "name";
    private static final String KEY_EX_DESCRIPTION = "description";
    private static final String KEY_EX_CATEGORY = "category";
    private static final String KEY_EX_VIDEO = "video_filename";

    // --- Table 2: Programs ---
    private static final String TABLE_PROGRAMS = "programs";
    private static final String KEY_PROG_ID = "id";
    private static final String KEY_PROG_NAME = "name";
    private static final String KEY_PROG_DESCRIPTION = "description";

    // --- Table 3: Program Days ---
    private static final String TABLE_PROGRAM_DAYS = "program_days";
    private static final String KEY_DAY_ID = "id";
    private static final String KEY_DAY_PROGRAM_ID = "program_id"; // Foreign Key
    private static final String KEY_DAY_NUMBER = "day_number";

    // --- Table 4: Day's Exercises (Linking Table) ---
    private static final String TABLE_DAY_EXERCISES = "day_exercises";
    private static final String KEY_LINK_DAY_ID = "day_id";       // Foreign Key
    private static final String KEY_LINK_EXERCISE_ID = "exercise_id"; // Foreign Key
    private static final String KEY_LINK_REPS = "reps";

    // --- Table 5: Journal ---
    private static final String TABLE_JOURNAL = "journal";
    private static final String KEY_JRNL_ID = "id";
    private static final String KEY_JRNL_DATE = "date_time";
    private static final String KEY_JRNL_PAIN = "pain_level";
    private static final String KEY_JRNL_EXERCISE_ID = "exercise_id"; // Optional


    // --- Constructor ---
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // --- onCreate (Called only ONCE, when the database is first created) ---
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create the Exercises table
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "(" +
                KEY_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_EX_NAME + " TEXT," +
                KEY_EX_DESCRIPTION + " TEXT," +
                KEY_EX_CATEGORY + " TEXT," +
                KEY_EX_VIDEO + " TEXT)";

        // SQL command to create the Programs table
        String CREATE_PROGRAMS_TABLE = "CREATE TABLE " + TABLE_PROGRAMS + "(" +
                KEY_PROG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PROG_NAME + " TEXT," +
                KEY_PROG_DESCRIPTION + " TEXT)";

        // SQL command to create the Program Days table
        String CREATE_PROGRAM_DAYS_TABLE = "CREATE TABLE " + TABLE_PROGRAM_DAYS + "(" +
                KEY_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_DAY_PROGRAM_ID + " INTEGER," +
                KEY_DAY_NUMBER + " INTEGER," +
                "FOREIGN KEY(" + KEY_DAY_PROGRAM_ID + ") REFERENCES " + TABLE_PROGRAMS + "(" + KEY_PROG_ID + "))";

        // SQL command to create the Linking table (DayExercises)
        String CREATE_DAY_EXERCISES_TABLE = "CREATE TABLE " + TABLE_DAY_EXERCISES + "(" +
                KEY_LINK_DAY_ID + " INTEGER," +
                KEY_LINK_EXERCISE_ID + " INTEGER," +
                KEY_LINK_REPS + " INTEGER," +
                "PRIMARY KEY(" + KEY_LINK_DAY_ID + ", " + KEY_LINK_EXERCISE_ID + "))";

        // SQL command to create the Journal table
        String CREATE_JOURNAL_TABLE = "CREATE TABLE " + TABLE_JOURNAL + "(" +
                KEY_JRNL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_JRNL_DATE + " INTEGER," + // Storing date as a long (Unix timestamp)
                KEY_JRNL_PAIN + " INTEGER," +
                KEY_JRNL_EXERCISE_ID + " INTEGER)";

        // Execute all the create table commands
        db.execSQL(CREATE_EXERCISES_TABLE);
        db.execSQL(CREATE_PROGRAMS_TABLE);
        db.execSQL(CREATE_PROGRAM_DAYS_TABLE);
        db.execSQL(CREATE_DAY_EXERCISES_TABLE);
        db.execSQL(CREATE_JOURNAL_TABLE);

        // --- NEW: Add initial data ---
        addInitialExercises(db);
    }

    // --- NEW: Private method to add sample data ---
    private void addInitialExercises(SQLiteDatabase db) {
        // We use ContentValues to add rows to the database
        ContentValues values = new ContentValues();

        // Sample 1: Knee Raise
        values.put(KEY_EX_NAME, "Seated Knee Raise");
        values.put(KEY_EX_DESCRIPTION, "Sit upright in a chair. Slowly lift one knee up as high as you comfortably can. Hold for 3 seconds and slowly lower it.");
        values.put(KEY_EX_CATEGORY, "Knee");
        values.put(KEY_EX_VIDEO, "knee_raise"); // You will name your video file "knee_raise.mp4"
        db.insert(TABLE_EXERCISES, null, values);

        // Sample 2: Shoulder Pendulum
        values.put(KEY_EX_NAME, "Shoulder Pendulum");
        values.put(KEY_EX_DESCRIPTION, "Lean forward, supporting yourself with one hand on a table. Let your other arm hang down and gently swing it in small circles.");
        values.put(KEY_EX_CATEGORY, "Shoulder");
        values.put(KEY_EX_VIDEO, "shoulder_pendulum"); // You will name this "shoulder_pendulum.mp4"
        db.insert(TABLE_EXERCISES, null, values);

        // Sample 3: Back Extension
        values.put(KEY_EX_NAME, "Prone Back Extension");
        values.put(KEY_EX_DESCRIPTION, "Lie on your stomach with your hands by your sides. Slowly lift your head and shoulders off the floor, using your back muscles. Hold for 2 seconds and lower.");
        values.put(KEY_EX_CATEGORY, "Back");
        values.put(KEY_EX_VIDEO, "back_extension"); // You will name this "back_extension.mp4"
        db.insert(TABLE_EXERCISES, null, values);
    }


    // --- onUpgrade (Called when you change the DATABASE_VERSION) ---
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For this project, a simple policy is fine.
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);

        // Create tables again
        onCreate(db);
    }

    // --- NEW: Function to get all exercises (for Feature 1) ---
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_EX_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_DESCRIPTION));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_CATEGORY));
                String videoFile = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_VIDEO));

                Exercise exercise = new Exercise(id, name, desc, category, videoFile);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return the list
        return exerciseList;
    }

    // --- NEW: Function to get a single exercise by ID (for Feature 1 Detail Screen) ---
    public Exercise getExerciseById(int exerciseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISES,
                new String[]{KEY_EX_ID, KEY_EX_NAME, KEY_EX_DESCRIPTION, KEY_EX_CATEGORY, KEY_EX_VIDEO},
                KEY_EX_ID + "=?",
                new String[]{String.valueOf(exerciseId)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_EX_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_NAME));
        String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_DESCRIPTION));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_CATEGORY));
        String videoFile = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EX_VIDEO));

        Exercise exercise = new Exercise(id, name, desc, category, videoFile);

        cursor.close();
        db.close();
        return exercise;
    }

    // --- We will add functions for Programs and Journal here later ---

}