package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.dtos.responses.MessageResponse;

import java.util.List;

public interface MessageService {

    MessageResponse sendMessage(String chatId, SendMessageRequest request);
    List<MessageResponse> getMessage(String chatId);
}
