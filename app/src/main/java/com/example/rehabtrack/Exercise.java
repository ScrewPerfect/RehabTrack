package com.example.rehabtrack;

// This class is a "model" for your exercise data.
public class Exercise {

    private int id;
    private String name;
    private String description;
    private String videoFileName; // We'll use this later

    // Constructor
    public Exercise(int id, String name, String description, String videoFileName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.videoFileName = videoFileName;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoFileName() {
        return videoFileName;
    }
}