package com.example.kafkasender.controller;

import com.example.kafkasender.model.MessageRequest;
import com.example.kafkasender.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173") // ca să accepte cereri din frontend
public class KafkaController {

    private final KafkaProducerService producerService;

    @Autowired
    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        producerService.sendMessage(request.getTopic(), request.getMessage());
        return ResponseEntity.ok("Mesaj trimis pe topic-ul: " + request.getTopic());
    }
}