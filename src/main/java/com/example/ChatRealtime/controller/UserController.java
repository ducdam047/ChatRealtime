package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.RegisterRequest;
import com.example.ChatRealtime.common.ApiResponse;
import com.example.ChatRealtime.dtos.responses.UserResponse;
import com.example.ChatRealtime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .code(201)
                .message("Register successfully")
                .data(userService.register(request))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
