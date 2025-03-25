package com.example.NotesApplication.Repositories;

import com.example.NotesApplication.Models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {


    @Transactional
    void deleteByTitle(String title);
    Notes findByTitle(String title);
}
