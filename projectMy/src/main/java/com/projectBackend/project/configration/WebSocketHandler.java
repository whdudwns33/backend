package com.projectBackend.project.configration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectBackend.project.dto.CommentMessageDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    @Getter
    private final Map<String, List<WebSocketSession>> userSessionMap = new ConcurrentHashMap<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            log.info("{}", payload);
            CommentMessageDTO commentMessage = objectMapper.readValue(payload, CommentMessageDTO.class);
            log.warn(commentMessage.getAuthorEmail());
            // 알림 메시지 생성
            Map<String, String> alertMessage = new HashMap<>();
            alertMessage.put("message", "새로운 댓글이 작성되었습니다: " + commentMessage.getCommentContent());
            String alertMessageJson = objectMapper.writeValueAsString(alertMessage);

            // 게시글 작성자의 세션 가져오기
            List<WebSocketSession> postAuthorSessions = userSessionMap.get(commentMessage.getAuthorEmail());

            // 게시글 작성자에게 알림 메시지 보내기
            if (postAuthorSessions == null) {
                log.warn("세션을 찾을 수 없음: " + commentMessage.getAuthorEmail());
            } else {
                for (WebSocketSession postAuthorSession : postAuthorSessions) {
                    if (postAuthorSession.isOpen()) {
                        postAuthorSession.sendMessage(new TextMessage(alertMessageJson));
                    } else {
                        log.warn("세션 연결이 닫혀 있음: " + commentMessage.getAuthorEmail());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error handling message: ", e);
        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String identifier = getEmailFromSession(session);
        if(identifier == null) {
            identifier = Objects.requireNonNull(session.getRemoteAddress()).getAddress().getHostAddress();
        }
        log.warn(identifier);
        userSessionMap.computeIfAbsent(identifier, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String identifier = getEmailFromSession(session);
        if(identifier == null) {
            identifier = Objects.requireNonNull(session.getRemoteAddress()).getAddress().getHostAddress();
        }
        List<WebSocketSession> sessions = userSessionMap.get(identifier);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessionMap.remove(identifier);
            }
        }
    }
    private String getEmailFromSession(WebSocketSession session) {
        try {
            String query = session.getUri().getQuery();
            if (query == null || query.isEmpty()) {
                return null;
            }
            Map<String, String> queryMap = Arrays.stream(query.split("&"))
                    .map(param -> param.split("="))
                    .filter(arr -> arr.length == 2)
                    .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
            String email = queryMap.get("email");

            // 이메일이 없는 경우 IP 주소를 반환합니다.
            if (email == null || email.isEmpty()) {
                return Objects.requireNonNull(session.getRemoteAddress()).getAddress().getHostAddress();
            }
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 예외가 발생한 경우에도 IP 주소를 반환합니다.
        return Objects.requireNonNull(session.getRemoteAddress()).getAddress().getHostAddress();
    }

}
