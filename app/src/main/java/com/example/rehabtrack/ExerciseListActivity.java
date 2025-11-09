package com.example.rehabtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// THIS IS THE ONLY CLASS THAT SHOULD BE IN THIS FILE
public class ExerciseListActivity extends AppCompatActivity {

    // 1. Create a simple array of strings for our exercise data
    String[] exerciseListData = {"Knee Bend", "Shoulder Stretch", "Leg Raise", "Wrist Flex"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line connects your Java code to your layout file (activity_exercise_list.xml)
        setContentView(R.layout.activity_exercise_list);

        // 2. Find the ListView in our layout
        ListView exerciseListView = findViewById(R.id.exerciseListView);

        // 3. Create an "Adapter"
        // An adapter takes your data (the array) and "adapts" it to be shown in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // A built-in, simple layout for one line of text
                exerciseListData // The data to display
        );

        // 4. Set the adapter on the ListView
        exerciseListView.setAdapter(adapter);

        // 5. Set a click listener for the list items
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the name of the exercise that was clicked
                String selectedExercise = exerciseListData[position];

                // Create an Intent to open the ExerciseDetailActivity
                Intent intent = new Intent(ExerciseListActivity.this, ExerciseDetailActivity.class);

                // "Put" the name of the exercise into the Intent so the next activity knows what was clicked
                intent.putExtra("EXERCISE_NAME", selectedExercise);

                // Start the detail activity
                startActivity(intent);
            }
        });
    }
}