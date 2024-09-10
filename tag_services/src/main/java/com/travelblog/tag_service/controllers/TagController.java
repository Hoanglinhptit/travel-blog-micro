package com.travelblog.tag_service.controllers;


import com.travelblog.tag_service.configs.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage() {
        kafkaProducerService.sendMessage("tag-topic", "Message from Tag Service");
        return ResponseEntity.ok("Message sent from Tag Service");
    }
}
