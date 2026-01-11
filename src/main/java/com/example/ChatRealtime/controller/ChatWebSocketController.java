package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat.send/{chatId}")
    public void sendMessage(
            @DestinationVariable String chatId,
            SendMessageRequest request,
            Principal principal) {

        if (principal == null)
            throw new RuntimeException("User not authentication");

        messageService.sendMessage(chatId, principal.getName(), request);
    }
}
