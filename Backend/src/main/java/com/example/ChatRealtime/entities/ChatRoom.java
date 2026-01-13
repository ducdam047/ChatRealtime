package com.example.ChatRealtime.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "chat_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String chatId;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    User user2;
}
