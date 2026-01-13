package com.example.ChatRealtime.dtos.requests;

import com.example.ChatRealtime.enums.MessageContentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMessageRequest {

    String content;
    MessageContentType type;
}
