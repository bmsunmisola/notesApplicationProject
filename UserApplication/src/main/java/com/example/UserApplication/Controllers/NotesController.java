package com.example.UserApplication.Controllers;

import com.example.UserApplication.Repository.UserRepository;
import com.example.UserApplication.Services.CircuitBreakerService;
import com.example.UserApplication.Services.RestApiService;
import com.example.UserApplication.UtilRecords;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("notes")
@RestController
public class NotesController {

    @Autowired
    private RestApiService restApiService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CircuitBreakerService circuitBreakerService;

    @PostMapping("/create")
    public String createUserNote(@RequestBody UtilRecords.Notes notes) {
        return restApiService.createNotes(notes);
    }

    @DeleteMapping("/delete/{title}")
    public String deleteNote(@PathVariable String title) {
        return restApiService.deleteNote(title);
    }

    @GetMapping("/randomNotes")
    public String getRandomNote() {
        return restApiService.getRandomNotes(); // ✅ Now correctly calls getRandomNotes() without an unnecessary parameter
    }

    @PutMapping("/updateNote/{title}") // Changed from @PostMapping to @PutMapping
    public String updateNote(@PathVariable String title) {
        return restApiService.updateNotes(title);
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/fallback")
    public ResponseEntity<String> getMeth() {
        if (circuitBreakerService.someMethod() == 1) {
            return new ResponseEntity<>("Service call succeeded", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Service Failed", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/fallback2")
    public String getMeths() {
        return "ok";
    }

    // Fallback method must be inside the class
    public ResponseEntity<String> fallbackMethod(Throwable throwable) {
        return new ResponseEntity<>("Fallback response due to error: " + throwable.getMessage(), HttpStatus.FORBIDDEN);
    }
}
