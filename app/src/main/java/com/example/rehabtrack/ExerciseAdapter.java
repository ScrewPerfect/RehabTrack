package com.example.rehabtrack;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseAdapter extends ArrayAdapter<String> {

    public ExerciseAdapter(@NonNull Context context, String[] exercises) {
        super(context, 0, exercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Get the data item for this position
        String exerciseName = getItem(position);

        // 2. Check if an existing view is being reused, otherwise inflate the NEW CARD LAYOUT
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_item, parent, false);
        }

        // 3. Lookup view for data population using the NEW IDs from our card layout
        TextView nameTextView = convertView.findViewById(R.id.cardExerciseName);
        ImageView checkmarkView = convertView.findViewById(R.id.cardCheckmark);

        // 4. Populate the data
        nameTextView.setText(exerciseName);

        // 5. Check if completed today
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String saveKey = exerciseName + "_" + today;
        SharedPreferences prefs = getContext().getSharedPreferences("RehabTrackData", Context.MODE_PRIVATE);
        boolean isCompleted = prefs.getBoolean(saveKey, false);

        // 6. Show or hide the checkmark based on completion status
        if (isCompleted) {
            checkmarkView.setVisibility(View.VISIBLE);
        } else {
            checkmarkView.setVisibility(View.GONE);
        }

        // 7. Return the completed view to render on screen
        return convertView;
    }
}