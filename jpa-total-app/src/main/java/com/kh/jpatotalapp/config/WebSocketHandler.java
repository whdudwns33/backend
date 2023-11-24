package com.kh.jpatotalapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.jpatotalapp.dto.ChatMessageDto;
import com.kh.jpatotalapp.dto.ChatRoomResDto;
import com.kh.jpatotalapp.entity.Chat;
import com.kh.jpatotalapp.repository.ChatRepository;
import com.kh.jpatotalapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {


    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.warn("{}", payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        String roomId = chatMessage.getRoomId();
        // 세션과 채팅방 ID를 매핑
        sessionRoomIdMap.put(session, chatMessage.getRoomId());

        if (chatMessage.getType() == ChatMessageDto.MessageType.ENTER) {
            chatService.addSessionAndHandleEnter(roomId, session, chatMessage);
        }

        else if (chatMessage.getType() == ChatMessageDto.MessageType.CLOSE) {
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }

        else {
            chatService.sendMessageToAll(roomId, chatMessage);
            // 채팅 저장
            try {
                Chat chat = new Chat();
                chat.setRoomId(roomId);
                chat.setMessage(chatMessage.getMessage());
                chat.setSender(chatMessage.getSender());
                chatRepository.save(chat);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("채팅이 저장되지 않았습니다.");
            }
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션과 매핑된 채팅방 ID 가져오기
        String roomId = sessionRoomIdMap.remove(session);
        if (roomId != null) {
            ChatMessageDto chatMessage = new ChatMessageDto();
            chatMessage.setType(ChatMessageDto.MessageType.CLOSE);
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }
    }
}