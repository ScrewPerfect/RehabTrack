package com.example.rehabtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ExerciseListActivity extends AppCompatActivity {

    String[] exerciseListData = {"Knee Bend", "Shoulder Stretch", "Leg Raise", "Wrist Flex"};
    ListView exerciseListView; // Moved to class level so we can access it later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        exerciseListView = findViewById(R.id.exerciseListView);

        // Set the initial adapter
        updateAdapter();

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedExercise = exerciseListData[position];
                Intent intent = new Intent(ExerciseListActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("EXERCISE_NAME", selectedExercise);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the list every time this screen becomes visible again.
        // This ensures that if they just completed an exercise and came back, the checkmark appears instantly.
        updateAdapter();
    }

    private void updateAdapter() {
        ExerciseAdapter adapter = new ExerciseAdapter(this, exerciseListData);
        exerciseListView.setAdapter(adapter);
    }
}