package com.example.kafkasender.controller;

import com.example.kafkasender.model.MessageRequest;
import com.example.kafkasender.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Kafka Producer API", description = "Endpoint-uri pentru trimiterea mesajelor în Kafka") // ADNOTARE NOUA
public class KafkaController {

    private final KafkaProducerService producerService;

    @Autowired
    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @Operation(summary = "Trimite un mesaj către un topic Kafka specificat", // ADNOTARE NOUA
            description = "Acest endpoint primește un payload JSON cu 'topic' și 'message' și îl trimite către broker-ul Kafka.") // ADNOTARE NOUA
    @ApiResponses(value = { // ADNOTARE NOUA
            @ApiResponse(responseCode = "200", description = "Mesaj trimis cu succes",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Cerere invalidă (ex: format JSON greșit sau topic invalid)",
                    content = @Content)
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody(
            description = "Detalii despre mesaj și topicul țintă", required = true) MessageRequest request) {
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