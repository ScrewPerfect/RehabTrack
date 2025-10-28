package com.example.rehabtrack;

public class Exercise {

    private int id;
    private String name;
    private String description;
    private String category;
    private String videoFilename;

    // A constructor to create a new Exercise object
    public Exercise(int id, String name, String description, String category, String videoFilename) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.videoFilename = videoFilename;
    }

    // --- Getters and Setters ---
    // Android Studio can generate these for you:
    // Right-click -> Generate -> Getters and Setters -> Select All

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVideoFilename() {
        return videoFilename;
    }

    public void setVideoFilename(String videoFilename) {
        this.videoFilename = videoFilename;
    }
}