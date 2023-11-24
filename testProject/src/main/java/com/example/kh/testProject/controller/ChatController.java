package com.example.kh.testProject.controller;


import com.example.kh.testProject.dto.ChatRoomReqDto;
import com.example.kh.testProject.dto.ChatRoomResDto;
import com.example.kh.testProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kh.testProject.utils.Common.CORS_ORIGIN;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomDto) {
        log.warn("chatRoomDto : {}", chatRoomDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomDto.getName());
        System.out.println(room.getRoomId());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public List<ChatRoomResDto> findAllRoom() {
        return chatService.findAllRoom();
    }

}
