package com.example.ChatRealtime.mapper;

import com.example.ChatRealtime.dtos.responses.MessageResponse;
import com.example.ChatRealtime.entities.Message;

public final class MessageMapper {

    private MessageMapper() {}

    public static MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .chatId(message.getChatRoom().getChatId())
                .sender(message.getSender().getUsername())
                .receiver(message.getReceiver().getUsername())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .seen(message.isSeen())
                .build();
    }
}
