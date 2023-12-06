package com.projectBackend.project.controller;

import com.projectBackend.project.dto.MemberReqDto;
import com.projectBackend.project.dto.MemberResDto;
import com.projectBackend.project.dto.TokenDto;
import com.projectBackend.project.jwt.TokenProvider;
import com.projectBackend.project.service.AuthService;
import com.projectBackend.project.service.MailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.projectBackend.project.service.MailService.EPW;

@Slf4j
@RestController
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final MailService mailService;

    // 회원가입
    @PostMapping("/sign")
    public ResponseEntity<MemberResDto> sign(@RequestBody MemberReqDto memberReqDto) {
        log.warn("memberReqDto {} :", memberReqDto);
        MemberResDto result = authService.signup(memberReqDto);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    // 회원 가입 시 이메일 인증 + 중복 체크
    @PostMapping("/mailConfirm")
    public ResponseEntity<Boolean> mailConfirm(@RequestParam String email) throws Exception {
        boolean isTrue = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 T or F : " + isTrue);
        return ResponseEntity.ok(isTrue);
    }

    // 인증 번호 체크
    @PostMapping("/ePw")
    public ResponseEntity<Boolean> checkEpw(@RequestParam String epw) {
        if (epw.equals(EPW)) {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }

    // 닉네임 중복 체크
    @GetMapping("/nickName")
    public ResponseEntity<Boolean> nickName(@RequestParam String nickName) {
        return ResponseEntity.ok(authService.isNickName(nickName));
    }



    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(authService.login(memberReqDto));
    }





   


}
