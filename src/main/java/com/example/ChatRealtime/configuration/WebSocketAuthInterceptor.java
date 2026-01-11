package com.example.ChatRealtime.configuration;

import com.example.ChatRealtime.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = extractToken(request);
        if (token == null || !jwtService.validateToken(token))
            return false;

        Long userId = jwtService.getUserIdFromToken(token);
        attributes.put("userId", userId.toString());
        log.info("WS handshake userId = {}", userId);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private String extractToken(ServerHttpRequest request) {
        URI uri = request.getURI();

        MultiValueMap<String, String> queryParams =
                UriComponentsBuilder.fromUri(uri)
                        .build()
                        .getQueryParams();

        return queryParams.getFirst("token");
    }
}
