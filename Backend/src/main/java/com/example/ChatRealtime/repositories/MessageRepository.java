package com.example.ChatRealtime.repositories;

import com.example.ChatRealtime.entities.ChatRoom;
import com.example.ChatRealtime.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatRoomOrderByTimestampAsc(ChatRoom chatRoom);
}
