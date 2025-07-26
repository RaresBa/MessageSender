package com.example.kafkasender.controller;

import com.example.kafkasender.service.KafkaConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumer")
@CrossOrigin(origins = "http://localhost:5173") // Asigură-te că portul este corect pentru frontend-ul tău
@Tag(name = "Kafka Consumer API", description = "Endpoint-uri pentru citirea mesajelor din Kafka")
public class MessageReaderController {

    private final KafkaConsumerService consumerService;

    @Autowired
    public MessageReaderController(KafkaConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Operation(summary = "Obține mesaje dintr-un topic Kafka specificat",
            description = "Returnează o listă de mesaje consumate anterior de la un anumit topic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mesaje returnate cu succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "204", description = "Niciun mesaj găsit pentru topicul specificat"),
            @ApiResponse(responseCode = "400", description = "Parametru 'topic' lipsă sau invalid")
    })
    @GetMapping("/messages")
    public ResponseEntity<List<String>> getMessagesByTopic(
            @Parameter(description = "Numele topicului din care se doresc mesajele", required = true, example = "messages")
            @RequestParam(required = true) String topic) {
        List<String> messages = consumerService.getMessagesForTopic(topic);
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Obține toate topicurile pentru care s-au primit mesaje",
            description = "Returnează o listă cu numele tuturor topicurilor din care consumer-ul a citit mesaje.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista topicurilor returnată cu succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "204", description = "Niciun topic cu mesaje găsit")
    })
    @GetMapping("/topics")
    public ResponseEntity<List<String>> getAllConsumedTopics() {
        List<String> topics = consumerService.getAllTopicsWithMessages();
        if (topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topics);
    }
}