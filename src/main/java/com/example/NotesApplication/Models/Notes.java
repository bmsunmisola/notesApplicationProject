package com.example.NotesApplication.Models;

import jakarta.persistence.*;

@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private int id;

    @Column(nullable = false, unique = true) // Title should be unique
    private String title;

    @Column(nullable = false)
    private String notes;

    public Notes() {}

    public Notes(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
