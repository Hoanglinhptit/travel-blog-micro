package com.travelblog.user_service.controllers;

import com.travelblog.user_service.configs.kafka.KafkaProducerService;
import com.travelblog.user_service.dtos.UserCreateRequestDto;
import com.travelblog.user_service.dtos.UserEntityDto;
import com.travelblog.user_service.dtos.UserUpdateRequestDto;
import com.travelblog.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserEntityDto> createUser(@RequestBody UserCreateRequestDto userCreateRequest) {
        return ResponseEntity.ok(userService.createUser(userCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntityDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequest) {
        return ResponseEntity.ok(userService.updateUser(userUpdateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<Page<UserEntityDto>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "") String email,
                                                           @RequestParam(defaultValue = "") String role,
                                                           @RequestParam(defaultValue = "") String name

                                                           ) {
        return ResponseEntity.ok(userService.getAllUsers(email, role, name, page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/send")
    public ResponseEntity<String> sendMessage() {
        kafkaProducerService.sendMessage("user-topic", "Message from User Service");
        return ResponseEntity.ok("Message sent from User Service");
    }
}
