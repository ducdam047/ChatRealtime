package com.example.ChatRealtime.service;

import com.example.ChatRealtime.dtos.responses.ChatRoomResponse;

public interface ChatRoomService {

    ChatRoomResponse createChatRoom(String username);
}
