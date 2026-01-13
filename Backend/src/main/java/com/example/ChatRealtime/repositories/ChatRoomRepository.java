package com.example.ChatRealtime.repositories;

import com.example.ChatRealtime.entities.ChatRoom;
import com.example.ChatRealtime.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUser1AndUser2(User user1, User user2);
    Optional<ChatRoom> findByChatId(String chatId);
}
