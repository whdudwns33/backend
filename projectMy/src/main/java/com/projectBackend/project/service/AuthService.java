package com.projectBackend.project.service;


import com.projectBackend.project.dto.UserReqDto;
import com.projectBackend.project.dto.UserResDto;
import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Music;
import com.projectBackend.project.entity.Token;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.repository.TokenRepository;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder; // 인증을 담당하는 클래스
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원 가입
    public UserResDto signup(UserReqDto requestDto) {
        if (userRepository.existsByUserEmail(requestDto.getUserEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toEntity(passwordEncoder);
        return UserResDto.of(userRepository.save(member));
    }

    // 로그인 및 토큰 저장, 발행
    public TokenDto login(UserReqDto requestDto) {
        try {
            System.out.println("requestDto 이메일 :" + requestDto.getUserEmail());
            System.out.println("requestDto 패스워드 :" + requestDto.getUserPassword());
            // 인증 코드 생성
            UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
            log.warn("authenticationToken: {}", authenticationToken);

            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            log.warn("authentication: {}", authentication);

            // 이메일을 참조하여 user 정보 조회
            String email = requestDto.getUserEmail();
            Optional<Member> userEntity = userRepository.findByUserEmail(email);
            System.out.println("로그인 userEntity : " + userEntity);
            if (userEntity.isPresent()) {
                // userEntity 객체의 정보를 데이터 베이스 객체로 생성
                Member user = userEntity.get();
                System.out.println("로그인 user : " + user);
                // 토큰 발급
                TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
                String refreshToken = tokenDto.getRefreshToken();
                // 토큰 저장
                Token token = new Token();
                token.setRefreshToken(refreshToken);
//                token.setEmail(email);
                token.setMember(user);
                tokenRepository.save(token);
                return tokenDto;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 로그인 상태 확인
    // 이메일을 참조하여 해당
    public boolean isLogined(String email) {
        try {
            if (email != null) {
                Optional<Member> member = userRepository.findByUserEmail(email);
                if (member.isPresent()) {
                    Member user = member.get();
                    System.out.println("로그인 정보 : " + user);
                    // 리프레쉬 토큰을 불러오기 위한 id
                    Long id = user.getId();
                    // 데이터 베이스의 리프레쉬 토큰
                    List<Token> tokens = tokenRepository.findAllByMemberId(id);
                    System.out.println("토큰 tokens : " + tokens);
                    // 가장 최근의 리프레쉬 데이터
                    if (!tokens.isEmpty()) {
                        Token token = tokens.get(tokens.size() - 1);
                            log.info("token : {}", token);
                            // 불러온 리프레쉬 토큰
                            String refreshToken = token.getRefreshToken();
                            log.info("refreshToken : {}", refreshToken);
                            // 토큰 유효성 체크
                            return tokenProvider.validateRefreshToken(refreshToken);
                        } else {
                            return false;
                    }
                } else {
                    System.out.println("해당 회원 정보가 없습니다.");
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("로그인 상태가 아닙니다.");
            return false;
        }
    }

    // accessToken 재발급
    public String createAccessToken(String refreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        return tokenProvider.generateAccessToken(authentication);
    }


    // 카카오 로그인 => 카카오 토큰이 존재하지만, 사용하지 않을 생각
    public TokenDto kakaoLogin(String email) {
        if (email != null) {
            // 랜덤 비밀번호 생성
            String password = generateRandomPassword();
            UserReqDto userReqDto = new UserReqDto();
            userReqDto.setUserEmail(email);
            userReqDto.setUserPassword(password);
            UsernamePasswordAuthenticationToken authenticationToken = userReqDto.toAuthentication();
            log.info("승인 토큰 : {}", authenticationToken);
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            log.info("승인 정보 : {}", authentication);
            return tokenProvider.generateTokenDto(authentication);
        }
        else {
            return null;
        }
    }


    // 카카오 로그인 이후 랜덤 패스워드 생성
    public String generateRandomPassword() {
        UUID uuid = UUID.randomUUID();

        // UUID를 문자열로 변환하고 '-' 제거
        String uuidAsString = uuid.toString().replaceAll("-", "");

        // 앞에서부터 10자리만 선택하여 랜덤 비밀번호로 사용
        String randomPassword = uuidAsString.substring(0, 10);

        return randomPassword;
    }

    // 이메일 중복 체크
    public boolean isEmail (String email) {
        if (email != null) {
            boolean isTrue = userRepository.existsByUserEmail(email);
            log.warn("이메일 중복 확인 {} : ", isTrue);
            return isTrue;
        }
        else {
            return false;
        }
    }

    // 닉네임 중복 체크
    public boolean isNickName(String nickName) {
        System.out.println("닉네임 : " + nickName);
        boolean isTrue = userRepository.existsByUserNickname(nickName);
        log.warn("닉네임 중복 확인 {} : ", isTrue);
        return isTrue;
    }






}
