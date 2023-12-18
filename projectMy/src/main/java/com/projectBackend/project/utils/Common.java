package com.projectBackend.project.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Common {
    // 토큰 발급을 요청하는 url
    public static String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";
    public static String clientId = "a42a4db55c114cff5770a883fc8607f9";
    public static String redirectUri = "http://localhost:8111/auth/kakao";
    public static String clientSecret = "Xs7FwH1FUNOkspaOszcuw2wZXTQGrEIs";

}
