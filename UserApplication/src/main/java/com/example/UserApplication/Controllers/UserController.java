package com.example.UserApplication.Controllers;

import com.example.UserApplication.Models.User;
import com.example.UserApplication.Repository.UserRepository;
import com.example.UserApplication.Services.RestApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RestApiService restApiService;


    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/users")
    public List<User> getAllItems() {
        return userRepository.findAll();
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/createuser")
    public User createItem(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
       return userRepository.save(user);
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PutMapping("/items/{id}")
    public User updateItem(@PathVariable Integer id, @RequestBody User user) {
        User oldProduct = userRepository.findById(id).orElseThrow();
        oldProduct.setUsername(user.getUsername());
        return userRepository.save(oldProduct);
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    //api calls to the second service
    record Product(String description, String productName,
                   Integer price, Integer amount){ }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/webFluxUsers")
    public List<User> getAllItemsClient() {
        return userRepository.findAll();
    }
}
