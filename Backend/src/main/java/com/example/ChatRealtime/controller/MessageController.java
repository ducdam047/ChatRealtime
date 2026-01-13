package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.common.ApiResponse;
import com.example.ChatRealtime.dtos.responses.MessageResponse;
import com.example.ChatRealtime.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{chatId}/send")
    public ApiResponse<MessageResponse> sendMessage(@PathVariable String chatId, @RequestBody SendMessageRequest request) {
        return ApiResponse.<MessageResponse>builder()
                .code(201)
                .message("Message sent")
                .data(messageService.sendMessage(chatId, request))
                .build();
    }

    @GetMapping("/{chatId}")
    public ApiResponse<List<MessageResponse>> getMessage(@PathVariable String chatId) {
        return ApiResponse.<List<MessageResponse>>builder()
                .code(200)
                .message("Message fetched")
                .data(messageService.getMessage(chatId))
                .build();
    }
}
