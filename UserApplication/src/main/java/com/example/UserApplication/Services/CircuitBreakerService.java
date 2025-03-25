package com.example.UserApplication.Services;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CircuitBreakerService {
    public int someMethod(){
        int num;
        if (Math.random() > 0.5){
            num = 0;
            throw new RuntimeException("Service failed");
        }
        else {
            num = 1;
        }
        return num;
    }
}
