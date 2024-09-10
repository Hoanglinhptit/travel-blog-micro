package com.travelblog.comment_service.controllers;

import com.travelblog.comment_service.configs.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage() {
        kafkaProducerService.sendMessage("comment-topic", "Message from Comment Service");
        return ResponseEntity.ok("Message sent from Comment Service");
    }
    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Message sent from Hello");
    }
}
