package com.projectBackend.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class TokenDto {
    private String grantType; // 인증 방식
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰
    private Long accessTokenExpiresIn; // 액세스 토큰 만료 시간
    private Long refreshTokenExpiresIn; // 리프레시 토큰 만료 시간
}
