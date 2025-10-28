package com.example.rehabtrack;

public class Program {

    private int id;
    private String name;
    private String description;

    // A constructor to create a new Program object
    public Program(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // --- Getters and Setters ---
    // You can also right-click in the editor and choose:
    // Generate -> Getters and Setters -> Select All

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
}

