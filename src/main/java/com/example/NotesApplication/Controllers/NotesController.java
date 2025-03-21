package com.example.NotesApplication.Controllers;

import com.example.NotesApplication.Models.Notes;
import com.example.NotesApplication.Repositories.NotesRepositories;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class NotesController {

    @Autowired
    private NotesRepositories notesRepositories;

    // Get all notes

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/GetallNotes")
    public List<Notes> getAllNotes() {
        return notesRepositories.findAll();
    }

    // Add multiple notes
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/addNotes")
    public List<Notes> addNotes(@RequestBody List<Notes> notes) {
        return notesRepositories.saveAll(notes);
    }

    // Create a single note
    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/postNote")
    public Notes createNote(@RequestBody Notes note) {
        return notesRepositories.save(note);
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PutMapping("/updateNote/{id}")
    public Notes updateNotes(
            @PathVariable Integer id,
            @RequestBody Notes notes){
        Notes oldNotes = notesRepositories.findById(id).orElseThrow();
        oldNotes.setNotes(notes.getNotes());
        return notesRepositories.save(oldNotes);
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @DeleteMapping("/deleteItem/{id}")
    public void deleteNotes(
            @PathVariable Integer id){
        notesRepositories.deleteById(id);}

    private final Random random = new Random();

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/randomNote")
    public Notes getRandomNotes(){
        List<Notes> allNotes = notesRepositories.findAll();
        if (allNotes.isEmpty()){
            throw new IllegalStateException("No notes available");
        }
        int index = random.nextInt(allNotes.size());
        return allNotes.get(index);
    }
}
