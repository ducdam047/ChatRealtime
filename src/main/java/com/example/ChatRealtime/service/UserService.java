package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.requests.RegisterRequest;
import com.example.ChatRealtime.dtos.responses.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);
}
