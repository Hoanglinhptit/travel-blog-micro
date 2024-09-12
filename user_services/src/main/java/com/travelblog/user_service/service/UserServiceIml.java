package com.travelblog.user_service.service;

import com.travelblog.user_service.configs.kafka.KafkaProducerService;
import com.travelblog.user_service.dtos.UserCreateRequestDto;
import com.travelblog.user_service.dtos.UserEntityDto;
import com.travelblog.user_service.dtos.UserUpdateRequestDto;
import com.travelblog.user_service.entites.UserEntity;
import com.travelblog.user_service.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {

    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final String USER_TOPIC = "user_topic";

    @Override
    public UserEntityDto createUser(UserCreateRequestDto userCreateDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userCreateDto.getEmail());
        user.setName(userCreateDto.getName());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRole(userCreateDto.getRole());
        user.setRole(userCreateDto.getRole() != null ? userCreateDto.getRole().toUpperCase() : "USER"); // Handle default role

        // If postIds is provided, set it; otherwise, initialize as empty list or handle accordingly
        user.setPostIds(userCreateDto.getPostIds() != null ? userCreateDto.getPostIds() : new ArrayList<>());// Manually setting complex type

//        BeanUtils.copyProperties(userCreateDto, user);
        UserEntity savedUser = userRepository.save(user);

        // Send message to Kafka
        kafkaProducerService.sendMessage(USER_TOPIC, "User created: " + savedUser.getId());

        return mapToDto(savedUser);
    }

    @Override
    public UserEntityDto updateUser(UserUpdateRequestDto userUpdateDto) {
        Optional<UserEntity> userOpt = userRepository.findById(userUpdateDto.getId());

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            BeanUtils.copyProperties(userUpdateDto, user);
            if (userUpdateDto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
            }

            UserEntity updatedUser = userRepository.save(user);

            // Send message to Kafka
            kafkaProducerService.sendMessage(USER_TOPIC, "User updated: " + updatedUser.getId());

            return mapToDto(updatedUser);
        }
        return null;
    }

    @Override
    public Optional<UserEntityDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public Optional<UserEntityDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToDto);
    }

    @Override
    public Page<UserEntityDto> getAllUsers(String email, String name, String role, int page, int size) {
        Page<UserEntity> userEntities = userRepository.searchUsers(
                (email == null || email.isEmpty()) ? null : email,
                (name == null || name.isEmpty()) ? null : name,
                (role == null || role.isEmpty()) ? null : role,
                PageRequest.of(page, size)
        );

        // Convert entities to DTOs
        return userEntities.map(this::mapToDto);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        // Send message to Kafka
        kafkaProducerService.sendMessage(USER_TOPIC, "User deleted: " + id);
    }

    private UserEntityDto mapToDto(UserEntity user) {
        UserEntityDto dto = new UserEntityDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}


