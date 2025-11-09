package com.example.rehabtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences; // <-- NEW IMPORT
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View; // <-- NEW IMPORT
import android.widget.Button; // <-- NEW IMPORT
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast; // <-- NEW IMPORT
import android.widget.VideoView;

import java.text.SimpleDateFormat; // <-- NEW IMPORT
import java.util.Date; // <-- NEW IMPORT
import java.util.Locale; // <-- NEW IMPORT

public class ExerciseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        TextView exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        TextView exerciseDescriptionTextView = findViewById(R.id.exerciseDescriptionTextView);
        VideoView exerciseVideoView = findViewById(R.id.exerciseVideoView);
        Button completeButton = findViewById(R.id.completeButton); // <-- FIND THE NEW BUTTON

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("EXERCISE_NAME");
        exerciseNameTextView.setText(exerciseName);

        String videoPath = "";
        if (exerciseName != null) {
            switch (exerciseName) {
                case "Knee Bend":
                    exerciseDescriptionTextView.setText("Sit on a chair. Gently extend your knee, hold for 5 seconds, and slowly lower it back down.");
                    videoPath = "android.resource://" + getPackageName() + "/" + R.raw.knee_raise;
                    break;
                case "Shoulder Stretch":
                    exerciseDescriptionTextView.setText("Reach one arm across your chest. Use your other arm to gently pull it closer. Hold for 15 seconds.");
                    videoPath = "android.resource://" + getPackageName() + "/" + R.raw.shoulder_stretch;
                    break;
                case "Leg Raise":
                    exerciseDescriptionTextView.setText("Lie on your back. Keep one leg straight and slowly raise it 6 inches off the floor. Hold, then lower.");
                    videoPath = "android.resource://" + getPackageName() + "/" + R.raw.leg_raise;
                    break;
                case "Wrist Flex":
                    exerciseDescriptionTextView.setText("Hold your arm out, palm facing up. Gently bend your wrist down with your other hand. Hold for 15 seconds.");
                    videoPath = "android.resource://" + getPackageName() + "/" + R.raw.wrist_flex;
                    break;
                default:
                    exerciseDescriptionTextView.setText("No description available.");
            }
        }

        if (!videoPath.isEmpty()) {
            Uri videoUri = Uri.parse(videoPath);
            exerciseVideoView.setVideoURI(videoUri);
            MediaController mediaController = new MediaController(this);
            exerciseVideoView.setMediaController(mediaController);
            mediaController.setAnchorView(exerciseVideoView);

            exerciseVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }

        // **************************************************
        // NEW: Handle the "Complete" button click
        // **************************************************
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Get today's date
                String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // 2. Create a unique key for saving (e.g., "Knee Bend_2023-10-27")
                String saveKey = exerciseName + "_" + today;

                // 3. Save it to SharedPreferences (simple internal storage)
                SharedPreferences prefs = getSharedPreferences("RehabTrackData", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(saveKey, true);
                editor.apply();

                // 4. Show a confirmation message
                Toast.makeText(ExerciseDetailActivity.this, "Great job! " + exerciseName + " completed for today.", Toast.LENGTH_SHORT).show();

                // 5. (Optional) Disable button so they can't click it twice
                completeButton.setEnabled(false);
                completeButton.setText("Completed Today ✅");
            }
        });

        // Check if already completed today when opening the screen
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String saveKey = exerciseName + "_" + today;
        SharedPreferences prefs = getSharedPreferences("RehabTrackData", MODE_PRIVATE);
        boolean isCompleted = prefs.getBoolean(saveKey, false);
        if (isCompleted) {
            completeButton.setEnabled(false);
            completeButton.setText("Completed Today ✅");
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}