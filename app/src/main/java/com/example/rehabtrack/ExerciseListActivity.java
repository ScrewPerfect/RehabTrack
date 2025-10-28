package com.example.rehabtrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This Activity displays the main list of all available exercises
 * using a RecyclerView. It implements our custom click listener
 * to handle when a user taps on an exercise.
 */
public class ExerciseListActivity extends AppCompatActivity implements ExerciseAdapter.OnItemClickListener {

    // --- UI Components ---
    private RecyclerView recyclerViewExercises;
    private Toolbar toolbar;

    // --- Data ---
    private DatabaseHelper dbHelper;
    private ArrayList<Exercise> exerciseList;
    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Set the layout from activity_exercise_list.xml
        setContentView(R.layout.activity_exercise_list);

        // 2. Initialize the Database Helper
        dbHelper = new DatabaseHelper(this);

        // 3. Find and set up the Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Exercises");

        // 4. Find the RecyclerView
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);

        // 5. Load the data from the database
        // We use the function we created in DatabaseHelper
        exerciseList = dbHelper.getAllExercises();

        // 6. Set up the RecyclerView
        // Create the adapter
        exerciseAdapter = new ExerciseAdapter(this, exerciseList);
        // Set the adapter on the RecyclerView
        recyclerViewExercises.setAdapter(exerciseAdapter);
        // Set the layout manager (e.g., a vertical list)
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));

        // 7. Set the click listener (this class implements the interface)
        exerciseAdapter.setOnItemClickListener(this);
    }

    /**
     * This method is required by the 'ExerciseAdapter.OnItemClickListener' interface.
     * It will be called by the adapter when an item is clicked.
     *
     * @param exerciseId The unique ID of the exercise that was clicked.
     */
    @Override
    public void onItemClick(int exerciseId) {
        // When an item is clicked, we open the Detail Activity

        // 1. Create an Intent to open ExerciseDetailActivity (we will create this next)
        Intent intent = new Intent(this, ExerciseDetailActivity.class);

        // 2. Pass the ID of the clicked exercise to the new activity.
        // This is how the detail activity knows which exercise to show.
        intent.putExtra("EXERCISE_ID", exerciseId);

        // 3. Start the new activity
        startActivity(intent);
    }
}
