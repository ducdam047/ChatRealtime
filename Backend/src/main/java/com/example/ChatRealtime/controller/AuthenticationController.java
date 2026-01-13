package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.LoginRequest;
import com.example.ChatRealtime.common.ApiResponse;
import com.example.ChatRealtime.dtos.responses.AuthenticationResponse;
import com.example.ChatRealtime.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody LoginRequest request) {
        var data = authenticationService.authenticate(request);
        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Login successfully")
                .data(data)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
