package com.example.ChatRealtime.controller;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat.send/{chatId}")
    public void sendMessage(
            @DestinationVariable String chatId,
            SendMessageRequest request,
            SimpMessageHeaderAccessor accessor) {
        Long senderId = (Long) accessor
                .getSessionAttributes()
                .get("USER_ID");

        if (senderId == null)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        messageService.sendMessage(chatId, senderId, request);
    }
}
