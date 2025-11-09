package com.example.rehabtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line connects your Java code to your layout file (activity_main.xml)
        setContentView(R.layout.activity_main);

        // 1. Find the button in our layout file
        Button startButton = findViewById(R.id.startButton);

        // 2. Set a click listener on the button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. When clicked, create an "Intent" to open the ExerciseListActivity
                Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);

                // 4. Start the new activity
                startActivity(intent);
            }
        });
    }
}
