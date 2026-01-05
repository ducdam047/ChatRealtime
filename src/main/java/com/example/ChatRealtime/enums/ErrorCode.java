package com.example.ChatRealtime.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_EXISTED(1001, "User already exists!", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    CHAT_ROOM_NOT_FOUND(1012, "Chat room not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003, "User not authenticated", HttpStatus.UNAUTHORIZED),
    INTERNAL_ERROR(9999, "Unexpected server error", HttpStatus.INTERNAL_SERVER_ERROR);
    int code;
    String message;
    HttpStatusCode statusCode;
}
