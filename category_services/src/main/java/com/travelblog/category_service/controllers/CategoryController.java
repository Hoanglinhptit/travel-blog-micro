package com.travelblog.category_service.controllers;

import com.travelblog.category_service.configs.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage() {
        kafkaProducerService.sendMessage("category-topic", "Message from Category Service");
        return ResponseEntity.ok("Message sent from Category Service");
    }
    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Message sent from Hello");
    }
}
