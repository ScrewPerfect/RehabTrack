package com.example.rehabtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

// --- CRITICAL IMPORT ---
import com.example.rehabtrack.R;
// -----------------------

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView historyTextView;
    private SharedPreferences prefs;

    // We check these specific exercises to see if they were saved in SharedPreferences
    private String[] allExercises = {"Knee Bend", "Shoulder Stretch", "Leg Raise", "Wrist Flex"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 1. Find Views
        calendarView = findViewById(R.id.calendarView);
        historyTextView = findViewById(R.id.historyTextView);

        // 2. Get Preferences
        prefs = getSharedPreferences("RehabTrackData", MODE_PRIVATE);

        // 3. Set Date Change Listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Note: month is 0-11, so we add 1 to get the correct format (e.g., 2025-11-12)
                String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
                checkDateHistory(selectedDate);
            }
        });

        // 4. Check today's history immediately when the screen opens
        Calendar today = Calendar.getInstance();
        String todayString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.getTime());
        checkDateHistory(todayString);
    }

    private void checkDateHistory(String date) {
        StringBuilder completedExercises = new StringBuilder();

        for (String exercise : allExercises) {
            // We look for keys like "Knee Bend_2025-11-12"
            String saveKey = exercise + "_" + date;
            boolean isCompleted = prefs.getBoolean(saveKey, false);

            if (isCompleted) {
                completedExercises.append("• " + exercise + "\n");
            }
        }

        if (completedExercises.length() == 0) {
            historyTextView.setText("No exercises completed on this day.");
        } else {
            historyTextView.setText(completedExercises.toString());
        }
    }
}