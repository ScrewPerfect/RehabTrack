package com.example.rehabtrack;

public class JournalEntry {

    private int id;
    private long dateTime; // Stored as a Unix timestamp (long)
    private int painLevel; // Stored as an integer (e.g., 1-10)

    // A constructor to create a new JournalEntry object
    public JournalEntry(int id, long dateTime, int painLevel) {
        this.id = id;
        this.dateTime = dateTime;
        this.painLevel = painLevel;
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }
}