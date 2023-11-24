package com.kh.jpatotalapp.entity;

import com.kh.jpatotalapp.dto.ChatMessageDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@ToString
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatId;

    private ChatMessageDto.MessageType type;
    private String roomId;
    private String message;
    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
