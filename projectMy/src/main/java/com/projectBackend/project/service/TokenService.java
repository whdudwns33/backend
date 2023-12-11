package com.projectBackend.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectBackend.project.entity.Token;
import com.projectBackend.project.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.projectBackend.project.utils.Common.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final TokenRepository tokenRepository;
//    private final TokenDto tokenDto;

    // 카카오 로그인 토큰 발급
    public String kakaoToken (String code) {
        try {
            //카카오로 post 방식 요청
            RestTemplate rt = new RestTemplate();

            //HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            //HttpBody 오브젝트 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("client_secret", clientSecret);
            params.add("code", code);

            //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                    new HttpEntity<>(params, headers);

            //Http 요청하기 - Post방식으로 그리고 response 변수의 응답받음.
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            log.info("response {} : ", response.getBody());
            System.out.println("status" + response.getStatusCodeValue());
            if (response.getStatusCodeValue() == 200) {
                // 카카오 토큰 정보를 저장하기 위한 객체 생성
                Token token = new Token();
                ObjectMapper objectMapper = new ObjectMapper();

                // JSON 문자열을 JsonNode로 변환 및 토큰 정보 출력
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                String accessToken = jsonNode.get("access_token").asText();
                String tokenType = jsonNode.get("token_type").asText();
                String refreshToken = jsonNode.get("refresh_token").asText();
                Long accessExpire = Long.valueOf(jsonNode.get("expires_in").asText());
                Long refreshExpire = Long.valueOf(jsonNode.get("refresh_token_expires_in").asText());

                log.info("Access Token: {}", accessToken);
                log.info("Token Type: {}", tokenType);
                log.info("Refresh Token: {}", refreshToken);

                // 데이터 저장
                tokenRepository.deleteAll();
                token.setGrantType(tokenType);
                token.setAccessToken(accessToken);
                token.setRefreshToken(refreshToken);
                token.setAccessTokenExpiresIn(accessExpire);
                token.setRefreshTokenExpiresIn(refreshExpire);
                tokenRepository.save(token);
                return "tur";
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("post 응답이 되지 않음");
            e.printStackTrace();
            return null;
        }
    }

    // 데이터 베이스의 토큰 정보 출력
    public String getToken() {
        List<Token> tokens = tokenRepository.findAll();
        // access 토큰 선언
        String accessToken = null;
        for (Token token : tokens) {
            accessToken = token.getAccessToken();
        }
        return accessToken;
    }

    // 카카오 이메일 받아오기
    public String kakaoEmail () {
        RestTemplate rt = new RestTemplate();
        String accessToken = getToken();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "bearer" + accessToken); // 여기에 액세스 토큰을 넣어주세요.

        // GET 요청하기 - response 변수의 응답받음.
        ResponseEntity<String> response = rt.getForEntity(
                "https://kapi.kakao.com/v2/user/me",
                String.class,
                headers
        );

        // ResponseEntity의 바디 부분만 반환
        return response.getBody();
    }



}
