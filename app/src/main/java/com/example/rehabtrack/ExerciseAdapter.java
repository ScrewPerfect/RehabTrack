package com.example.rehabtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * ExerciseAdapter is the "bridge" between our data (ArrayList<Exercise>)
 * and our UI (the RecyclerView). It tells the RecyclerView how to display each item.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    // --- FIX: Made fields 'final' as suggested ---
    private final Context context;
    private final ArrayList<Exercise> exerciseList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int exerciseId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    // Constructor
    public ExerciseAdapter(Context context, ArrayList<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    /**
     * Called when RecyclerView needs a new "row" (ViewHolder) to be created.
     */
    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single row (exercise_list_item.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_list_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        // Get the data model based on position
        Exercise currentExercise = exerciseList.get(position);

        // Set item data to the views
        holder.nameTextView.setText(currentExercise.getName());
        holder.categoryTextView.setText(currentExercise.getCategory());
    }

    /**
     * Called by RecyclerView to get the total number of items in the list.
     */
    @Override
    public int getItemCount() {
        return (exerciseList == null) ? 0 : exerciseList.size();
    }


    /**
     * The ViewHolder class.
     * It holds references to the UI elements in our 'exercise_list_item.xml' layout.
     */
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView categoryTextView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.textViewExerciseName);
            categoryTextView = itemView.findViewById(R.id.textViewExerciseCategory);

            // --- FIX: Converted to lambda and used getBindingAdapterPosition() ---
            itemView.setOnClickListener(v -> {
                // FIX: Use getBindingAdapterPosition() as getAdapterPosition() is deprecated
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    Exercise clickedExercise = exerciseList.get(position);
                    listener.onItemClick(clickedExercise.getId());
                }
            });
        }
    }

}