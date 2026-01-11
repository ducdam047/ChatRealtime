package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.dtos.responses.MessageResponse;

import java.util.List;

public interface MessageService {

    MessageResponse sendMessage(String chatId, SendMessageRequest request);
    MessageResponse sendMessage(String chatId, String senderId, SendMessageRequest request);
    List<MessageResponse> getMessage(String chatId);
}
