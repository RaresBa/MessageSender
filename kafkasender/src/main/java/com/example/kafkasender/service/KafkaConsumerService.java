package com.example.kafkasender.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumerService {

    // Stochează mesajele pe topic. Cheia este numele topicului, valoarea este o listă de mesaje.
    private final Map<String, List<String>> messagesByTopic = new HashMap<>();

    @KafkaListener(topics = "#{'${kafka.consumer.topics}'.split(',')}", groupId = "my-spring-boot-group")
    public void consumeMessage(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Mesaj primit pe topicul '" + topic + "': " + message);
        messagesByTopic.computeIfAbsent(topic, k -> new ArrayList<>()).add(message);
    }

    public List<String> getMessagesForTopic(String topic) {
        return messagesByTopic.getOrDefault(topic, new ArrayList<>());
    }

    public List<String> getAllTopicsWithMessages() {
        return new ArrayList<>(messagesByTopic.keySet());
    }
}