package com.projectBackend.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmsService {


    // 인증 번호 생성.
    // 인증 번호 생성 메서드
    public String generateAuthCode() {
        // 인증 번호에 사용할 문자열
        String chars = "0123456789";

        // Random 객체 생성
        Random random = new Random();

        // StringBuilder를 사용하여 인증 번호 생성
        StringBuilder authCodeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            authCodeBuilder.append(randomChar);
        }

        String cn = authCodeBuilder.toString();
        System.out.println("문자인증 인증번호 : " + cn);
        // 생성된 인증 번호 반환
        return cn;
    }


}
