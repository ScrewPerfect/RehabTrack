package com.example.rehabtrack;

// --- IMPORT STATEMENTS ---
// Make sure all of these are here, especially the "R" file
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.rehabtrack.R; // <-- THIS IS THE CRITICAL IMPORT

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
// -------------------------


public class HistoryActivity extends AppCompatActivity {

    // --- CLASS-LEVEL VARIABLES ---
    private CalendarView calendarView;
    private TextView historyTextView;
    private SharedPreferences prefs;
    private static final String TAG = "HistoryActivity";

    // This array lists all exercises. It's needed to check the save file.
    private String[] allExercises = {"Knee Bend", "Shoulder Stretch", "Leg Raise", "Wrist Flex"};
    // -----------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line will now work because 'R' can be found
        setContentView(R.layout.activity_history);

        // --- CODE INSIDE onCreate() ---
        Log.d(TAG, "onCreate: HistoryActivity started.");

        // 1. Find the views from the layout file
        calendarView = findViewById(R.id.calendarView);
        historyTextView = findViewById(R.id.historyTextView);

        // 2. Get the save file
        prefs = getSharedPreferences("RehabTrackData", MODE_PRIVATE);

        // 3. Log all saved data (for debugging)
        logAllSavedData();

        // 4. Set a listener to check for new dates when clicked
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 'month' is 0-based, so we must add 1
                String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
                Log.d(TAG, "Date selected: " + selectedDate);
                checkDateHistory(selectedDate);
            }
        });

        // 5. Check history for today's date right when the screen opens
        Calendar today = Calendar.getInstance();
        String todayString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.getTime());
        Log.d(TAG, "Checking history for today: " + todayString);
        checkDateHistory(todayString);
    }

    /**
     * Checks the SharedPreferences for completed exercises on a specific date.
     * @param date The date to check, formatted as "yyyy-MM-dd".
     */
    private void checkDateHistory(String date) {
        Log.d(TAG, "Checking history for date: " + date);
        StringBuilder completedExercises = new StringBuilder();

        // Loop through our list of all possible exercises
        for (String exercise : allExercises) {
            // Create the save key (e.g., "Knee Bend_2025-11-12")
            String saveKey = exercise + "_" + date;
            boolean isCompleted = prefs.getBoolean(saveKey, false);

            Log.d(TAG, "Checking key: '" + saveKey + "' ... Found: " + isCompleted);

            if (isCompleted) {
                completedExercises.append("• " + exercise + "\n");
            }
        }

        // Update the TextView at the bottom
        if (completedExercises.length() == 0) {
            historyTextView.setText("No exercises completed on this day.");
        } else {
            historyTextView.setText(completedExercises.toString());
        }
    }

    /**
     * A debug function to print all saved data to the Logcat.
     */
    private void logAllSavedData() {
        Log.d(TAG, "--- Dumping all SharedPreferences ---");
        Map<String, ?> allEntries = prefs.getAll();
        if (allEntries.isEmpty()) {
            Log.d(TAG, "SharedPreferences is EMPTY.");
        } else {
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d(TAG, "Key: '" + entry.getKey() + "', Value: " + entry.getValue().toString());
            }
        }
        Log.d(TAG, "-------------------------------------");
    }
}