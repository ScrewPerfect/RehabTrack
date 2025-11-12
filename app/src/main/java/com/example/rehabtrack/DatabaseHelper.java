package com.example.rehabtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rehabtrack.db";
    private static final int DATABASE_VERSION = 1;

    // Table for Exercises
    private static final String TABLE_EXERCISES = "exercises";
    private static final String COLUMN_EX_ID = "id";
    private static final String COLUMN_EX_NAME = "name";
    private static final String COLUMN_EX_DESCRIPTION = "description";
    private static final String COLUMN_EX_VIDEO_FILE = "video_file";

    // (You can add other tables here, like for your Journal)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Exercises table
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + COLUMN_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EX_NAME + " TEXT,"
                + COLUMN_EX_DESCRIPTION + " TEXT,"
                + COLUMN_EX_VIDEO_FILE + " TEXT" + ")";
        db.execSQL(CREATE_EXERCISES_TABLE);

        // --- Pre-load the table with your exercises ---
        addExercise(db, "Knee Bend", "Sit on a chair. Gently extend your knee, hold for 5 seconds...", "knee_bend");
        addExercise(db, "Shoulder Stretch", "Reach one arm across your chest. Use your other arm...", "shoulder_stretch");
        addExercise(db, "Leg Raise", "Lie on your back. Keep one leg straight and slowly raise it...", "leg_raise");
        addExercise(db, "Wrist Flex", "Hold your arm out, palm facing up. Gently bend your wrist...", "wrist_flex");
    }

    // Helper method to add the initial exercises
    private void addExercise(SQLiteDatabase db, String name, String desc, String videoFile) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EX_NAME, name);
        values.put(COLUMN_EX_DESCRIPTION, desc);
        values.put(COLUMN_EX_VIDEO_FILE, videoFile);
        db.insert(TABLE_EXERCISES, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

    // --- Method to get all exercises from the database ---
    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXERCISES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EX_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_DESCRIPTION));
                String videoFile = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_VIDEO_FILE));

                exerciseList.add(new Exercise(id, name, desc, videoFile));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return exerciseList;
    }
}