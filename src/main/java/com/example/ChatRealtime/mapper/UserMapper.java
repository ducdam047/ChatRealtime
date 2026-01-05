package com.example.ChatRealtime.mapper;

import com.example.ChatRealtime.dtos.responses.UserResponse;
import com.example.ChatRealtime.entities.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        if(user == null) return null;
        return UserResponse.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .build();
    }
}
