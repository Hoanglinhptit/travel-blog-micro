package com.travelblog.user_service.dtos;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequestDto {
    private String email;
    private String name;
    private String password;
    private String role;

    @Nullable
    private List<Integer> postIds;
}
