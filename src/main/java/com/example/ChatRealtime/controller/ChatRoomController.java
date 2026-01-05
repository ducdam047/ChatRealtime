package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.responses.ApiResponse;
import com.example.ChatRealtime.dtos.responses.ChatRoomResponse;
import com.example.ChatRealtime.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create/{username}")
    ApiResponse<ChatRoomResponse> createChatRoom(@PathVariable String username) {
        return ApiResponse.<ChatRoomResponse>builder()
                .code(200)
                .message("Chat room created successfully")
                .data(chatRoomService.createChatRoom(username))
                .build();
    }
}
