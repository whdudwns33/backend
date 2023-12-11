package com.projectBackend.project.repository;

import com.projectBackend.project.entity.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

class TokenRepositoryTest {
    @Autowired
    TokenRepository tokenRepository;

    public void createToken () {
        Token token = new Token();
        token.setGrantType("bearer");
        token.setAccessToken("ldq6j9tO_o-wZgGvu1rS0Djgrb6ed3xkwJYKKiWRAAABjFesODevm_uHqQwxKA");
        token.setAccessTokenExpiresIn(Long.valueOf("21599"));
        token.setRefreshToken("2w07345yBIe9LLav77YiJvc6Aq1hjOAKtHsKKiWRAAABjFesODSvm_uHqQwxKA");
        token.setRefreshTokenExpiresIn(Long.valueOf("5183999"));
        tokenRepository.save(token);
    }

    @Test
    @DisplayName("토큰 정보 출력")
    public void tokenList  () {
        List<Token> tokens = tokenRepository.findAll();
        for (Token token : tokens) {
            String accessToken = token.getAccessToken();
        }
    }
}