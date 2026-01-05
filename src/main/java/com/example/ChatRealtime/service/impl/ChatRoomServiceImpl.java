package com.example.ChatRealtime.service.impl;

import com.example.ChatRealtime.dtos.responses.ChatRoomResponse;
import com.example.ChatRealtime.entities.ChatRoom;
import com.example.ChatRealtime.entities.User;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.mapper.ChatRoomMapper;
import com.example.ChatRealtime.repositories.ChatRoomRepository;
import com.example.ChatRealtime.repositories.UserRepository;
import com.example.ChatRealtime.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ChatRoomResponse createChatRoom(String targetUsername) {
        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user1 = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User user2 = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User firstUser = user1.getId() < user2.getId() ? user1 : user2;
        User secondUser = firstUser == user1 ? user2 : user1;

        ChatRoom chatRoom = chatRoomRepository.findByUser1AndUser2(firstUser, secondUser)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .chatId(UUID.randomUUID().toString())
                            .user1(firstUser)
                            .user2(secondUser)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        return ChatRoomMapper.toResponse(chatRoom);
    }
}
