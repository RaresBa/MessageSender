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
        String topic = request.getTopic();
        if (topic == null || !topic.matches("^[a-zA-Z0-9._-]+$")) {
            return ResponseEntity.badRequest().body("Invalid topic name! Only letters, numbers, dot, underscore, and dash are allowed. No spaces.");
        }
        producerService.sendMessage(topic, request.getMessage());
        return ResponseEntity.ok("Mesaj trimis pe topic-ul: " + topic);
    }

    @GetMapping("/topics")
    public ResponseEntity<?> getTopics() {
        return ResponseEntity.ok(producerService.listTopics());
    }
}