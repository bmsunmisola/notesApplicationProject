package com.example.NotesApplication.Controllers;

import com.example.NotesApplication.Models.Notes;
import com.example.NotesApplication.Repositories.NotesRepositories;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/notes") // Base URL for all endpoints
public class NotesController {

    @Autowired
    private NotesRepositories notesRepositories;

    private final Random random = new Random();

    // ✅ Get all notes
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/getAll")
    public List<Notes> getAllNotes() {
        return notesRepositories.findAll();
    }

    // ✅ Add multiple notes
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/add")
    public List<Notes> addNotes(@RequestBody List<Notes> notes) {
        return notesRepositories.saveAll(notes);
    }

    // ✅ Create a single note
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/create")
    public Notes createNote(@RequestBody Notes note) {
        return notesRepositories.save(note);
    }

    // ✅ Update a note by title
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PutMapping("/update/{title}")
    public Notes updateNotes(@PathVariable String title, @RequestBody Notes notes) {
        Notes oldNotes = notesRepositories.findByTitle(title);
        oldNotes.setNotes(notes.getNotes());
        return notesRepositories.save(oldNotes);
    }

    // ✅ Delete a note by title
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @DeleteMapping("/delete/{title}")
    public ResponseEntity<String> deleteNotes(@PathVariable String title) {
        notesRepositories.deleteByTitle(title);
        return ResponseEntity.ok("Note deleted successfully");
    }

    // ✅ Get a random note
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/random")
    public Notes getRandomNotes() {
        List<Notes> allNotes = notesRepositories.findAll();
        if (allNotes.isEmpty()) {
            throw new IllegalStateException("No notes available");
        }
        return allNotes.get(random.nextInt(allNotes.size()));
    }

    // ✅ Fallback method for CircuitBreaker
    public ResponseEntity<String> fallbackMethod(Exception e) {
        return ResponseEntity.status(503).body("Service is temporarily unavailable: " + e.getMessage());
    }
}
