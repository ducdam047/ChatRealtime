package com.example.ChatRealtime.service.impl;

import com.example.ChatRealtime.dtos.requests.RegisterRequest;
import com.example.ChatRealtime.dtos.responses.UserResponse;
import com.example.ChatRealtime.entities.User;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.mapper.UserMapper;
import com.example.ChatRealtime.repositories.UserRepository;
import com.example.ChatRealtime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }
}
