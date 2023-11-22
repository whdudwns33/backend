package com.kh.totalJpaSample.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 캡슐화. 게터와 세터
@Getter
@Setter
// Data Transfer Object : 계층간 데이터를 전송하기 위한 객체, 프론트엔드와 JSON으로 통신하기 위함
// 요청과 응답에 대한 객체
public class MemberDto {
    private String userId;
    private String email;
    private String password;
    private String name;
    private LocalDateTime regDate;
}
