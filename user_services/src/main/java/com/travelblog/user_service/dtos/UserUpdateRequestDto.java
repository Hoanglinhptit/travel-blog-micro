package com.travelblog.user_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class UserUpdateRequestDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;
    private List<Integer> postIds;
}
