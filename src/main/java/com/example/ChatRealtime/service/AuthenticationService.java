package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.requests.LoginRequest;
import com.example.ChatRealtime.dtos.responses.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(LoginRequest request);
}
