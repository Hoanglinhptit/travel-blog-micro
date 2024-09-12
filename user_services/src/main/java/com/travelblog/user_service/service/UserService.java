package com.travelblog.user_service.service;

import com.travelblog.user_service.dtos.UserCreateRequestDto;
import com.travelblog.user_service.dtos.UserEntityDto;
import com.travelblog.user_service.dtos.UserUpdateRequestDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    UserEntityDto createUser(UserCreateRequestDto userCreateDto);
    UserEntityDto updateUser(UserUpdateRequestDto userUpdateDto);
    Optional<UserEntityDto> getUserById(Long id);
    Optional<UserEntityDto> getUserByEmail(String email);

    Page<UserEntityDto> getAllUsers(String email, String name, String role, int page, int size);
    void deleteUser(Long id);
}
