package com.example.NotesApplication.Models;

import jakarta.persistence.*;

@Entity
public class Notes {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    int id;
    private String notes;
    private String title;

    public Notes() {
    }

    public Notes(String notes, String title) {
        this.notes = notes;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
