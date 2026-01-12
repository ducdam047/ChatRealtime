package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.requests.RegisterRequest;
import com.example.ChatRealtime.dtos.responses.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);
    List<UserResponse> getUsers();
}
