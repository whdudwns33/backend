package com.projectBackend.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // One-to-One 매핑
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member user;

    @Column(name = "TOKEN_USER_EMAIL")
    private String email;
//    private String grantType; // 인증 방식
//    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰
//    private Long accessTokenExpiresIn; // 액세스 토큰 만료 시간
//    private Long refreshTokenExpiresIn; // 리프레시 토큰 만료 시간
}
