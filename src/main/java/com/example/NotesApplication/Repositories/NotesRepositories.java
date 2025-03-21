package com.example.NotesApplication.Repositories;

import com.example.NotesApplication.Models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {
}
