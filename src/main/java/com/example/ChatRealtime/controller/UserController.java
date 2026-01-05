package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.RegisterRequest;
import com.example.ChatRealtime.dtos.responses.ApiResponse;
import com.example.ChatRealtime.dtos.responses.UserResponse;
import com.example.ChatRealtime.entities.User;
import com.example.ChatRealtime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(201)
                .message("Register successfully")
                .data(userService.register(request))
                .build();
    }
}
