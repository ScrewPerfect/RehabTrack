package com.example.rehabtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // <-- Make sure to import Button

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- ADD THIS CODE ---
        Button exerciseButton = findViewById(R.id.btnViewExercises);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will open your ExerciseListActivity
                Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
                startActivity(intent);
            }
        });
        // --- END OF ADDED CODE ---
    }
}