package com.example.UserApplication.Services;

import com.example.UserApplication.UtilRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RestApiService {

    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public RestApiService(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }





    public String createNotes(UtilRecords.Notes notes) {
        String url = "http://localhost:8089/postNote";

        return webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(notes)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String updateNotes(String title){
        String url = "http://localhost:8089/updateNotes" + title;

        return webClientBuilder.build()
                .put()
                .uri(url)
                .bodyValue(title)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteNote(String title) {
        String url = "http://localhost:8089/delete/" + title;

        return webClientBuilder.build()
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getRandomNotes() {
        String url = "http://localhost:8089/getRandomNotes";
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
