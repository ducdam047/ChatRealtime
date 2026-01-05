package com.example.ChatRealtime.dtos.responses;

import com.example.ChatRealtime.enums.MessageContentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {

    Long id;
    String chatId;
    String sender;
    String receiver;
    String content;
    LocalDateTime timestamp;
    boolean seen;
}
