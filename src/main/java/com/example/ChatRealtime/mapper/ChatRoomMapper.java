package com.example.ChatRealtime.mapper;

import com.example.ChatRealtime.dtos.responses.ChatRoomResponse;
import com.example.ChatRealtime.entities.ChatRoom;

public final class ChatRoomMapper {

    private ChatRoomMapper() {}

    public static ChatRoomResponse toResponse(ChatRoom chatRoom) {
        if(chatRoom == null) return null;
        return ChatRoomResponse.builder()
                .chatId(chatRoom.getChatId())
                .user1(chatRoom.getUser1().getUsername())
                .user2(chatRoom.getUser2().getUsername())
                .build();
    }
}
