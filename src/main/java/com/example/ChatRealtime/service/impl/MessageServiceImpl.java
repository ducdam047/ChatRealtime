package com.example.ChatRealtime.service.impl;

import com.example.ChatRealtime.dtos.requests.SendMessageRequest;
import com.example.ChatRealtime.dtos.responses.MessageResponse;
import com.example.ChatRealtime.entities.ChatRoom;
import com.example.ChatRealtime.entities.Message;
import com.example.ChatRealtime.entities.User;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.mapper.MessageMapper;
import com.example.ChatRealtime.repositories.ChatRoomRepository;
import com.example.ChatRealtime.repositories.MessageRepository;
import com.example.ChatRealtime.repositories.UserRepository;
import com.example.ChatRealtime.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public MessageResponse sendMessage(String chatId, SendMessageRequest request) {
        String senderUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        ChatRoom chatRoom = chatRoomRepository.findByChatId(chatId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User receiver = chatRoom.getUser1().getId().equals(sender.getId())
                ? chatRoom.getUser2()
                : chatRoom.getUser1();

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();
        messageRepository.save(message);

        return MessageMapper.toResponse(message);
    }

    @Transactional
    @Override
    public MessageResponse sendMessage(String chatId, String senderId, SendMessageRequest request) {
        Long senderIdLong = Long.valueOf(senderId);

        ChatRoom chatRoom = chatRoomRepository.findByChatId(chatId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        User sender = userRepository.findById(senderIdLong)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        User receiver = chatRoom.getUser1().getId().equals(sender.getId())
                ? chatRoom.getUser2()
                : chatRoom.getUser1();

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();
        messageRepository.save(message);

        MessageResponse response = MessageMapper.toResponse(message);

        log.debug("WS PUSH | chatId={} | from={} | to={}", chatId, sender.getId(), receiver.getId());

        simpMessagingTemplate.convertAndSendToUser(
                receiver.getId().toString(),
                "/queue/message",
                response
        );
        simpMessagingTemplate.convertAndSendToUser(
                sender.getId().toString(),
                "queue/message",
                response
        );

        return response;
    }

    @Override
    public List<MessageResponse> getMessage(String chatId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatId(chatId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        return messageRepository
                .findByChatRoomOrderByTimestampAsc(chatRoom)
                .stream()
                .map(MessageMapper::toResponse)
                .toList();
    }
}
