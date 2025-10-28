package com.example.rehabtrack;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class ExerciseDetailActivity extends AppCompatActivity {

    // --- UI Components ---
    private Toolbar toolbar;
    private VideoView videoView;
    private TextView tvName, tvCategory, tvDescription;
    private Button btnStart;

    // --- Data ---
    private DatabaseHelper dbHelper;
    private Exercise currentExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail); // <-- This links to your XML file

        // 1. Initialize DB Helper
        dbHelper = new DatabaseHelper(this);

        // 2. Find all UI views from your XML
        toolbar = findViewById(R.id.toolbarDetail);
        videoView = findViewById(R.id.videoViewExercise);
        tvName = findViewById(R.id.textViewDetailName);
        tvCategory = findViewById(R.id.textViewDetailCategory);
        tvDescription = findViewById(R.id.textViewDetailDescription);
        btnStart = findViewById(R.id.buttonStartExercise);

        // 3. Set up the Toolbar
        setSupportActionBar(toolbar);
        // Add null check and back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Make back button work

        // 4. Get the Exercise ID from the Intent (sent by ExerciseListActivity)
        int exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);

        // 5. Check if the ID is valid
        if (exerciseId == -1) {
            Toast.makeText(this, "Error: Exercise not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 6. Load the exercise from the database
        currentExercise = dbHelper.getExerciseById(exerciseId);

        // 7. Populate the UI with data
        if (currentExercise != null) {
            getSupportActionBar().setTitle(currentExercise.getName()); // Set toolbar title
            tvName.setText(currentExercise.getName());
            tvCategory.setText(currentExercise.getCategory());
            tvDescription.setText(currentExercise.getDescription());

            // 8. Set up the Video
            playVideo(currentExercise.getVideoFilename());
        } else {
            Toast.makeText(this, "Error: Could not load exercise", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 9. Set up the "Start Exercise" button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will link to Feature 2 (ExercisePlayerActivity)
                Toast.makeText(ExerciseDetailActivity.this, "Starting " + currentExercise.getName(), Toast.LENGTH_SHORT).show();

                // --- LATER, YOU WILL DO THIS ---
                // Intent intent = new Intent(ExerciseDetailActivity.this, ExercisePlayerActivity.class);
                // intent.putExtra("EXERCISE_ID", currentExercise.getId());
                // startActivity(intent);
            }
        });
    }

    private void playVideo(String videoFilename) {
        // Videos must be in the 'res/raw' folder.
        int videoResourceId = getResources().getIdentifier(videoFilename, "raw", getPackageName());

        if (videoResourceId == 0) {
            Toast.makeText(this, "Error: Video file not found (" + videoFilename + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Add media controls (play, pause)
        videoView.setMediaController(new android.widget.MediaController(this));

        // Add looping
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        videoView.start(); // Start playing the video
    }
}
