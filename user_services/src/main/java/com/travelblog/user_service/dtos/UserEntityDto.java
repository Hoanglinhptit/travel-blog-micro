package com.travelblog.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.travelblog.user_service.entites.UserEntity}
 */

@Data
public class UserEntityDto implements Serializable {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long id;
    String email;
    String name;
    String password;
    String role;
    List<Integer> postIds;
}