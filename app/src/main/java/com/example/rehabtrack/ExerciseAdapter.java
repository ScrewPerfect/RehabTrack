package com.example.rehabtrack;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        // 2. Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // 3. Lookup view for data population
        TextView textView = convertView.findViewById(android.R.id.text1);

        // 4. Check if completed today
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String saveKey = exerciseName + "_" + today;
        SharedPreferences prefs = getContext().getSharedPreferences("RehabTrackData", Context.MODE_PRIVATE);
        boolean isCompleted = prefs.getBoolean(saveKey, false);

        // 5. Populate the data into the template view using the data object
        if (isCompleted) {
            textView.setText(exerciseName + " ✅");
        } else {
            textView.setText(exerciseName);
        }

        // 6. Return the completed view to render on screen
        return convertView;
    }
}