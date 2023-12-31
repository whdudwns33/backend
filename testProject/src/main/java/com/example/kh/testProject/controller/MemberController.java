package com.example.kh.testProject.controller;


import com.example.kh.testProject.dto.MemberDto;
import com.example.kh.testProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    // 멤버 객체로 생성
    private final MemberService memberService;

    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }

    // 총 페이지 수
    @GetMapping("/list/count")
    public ResponseEntity<Integer> memberCount(@RequestParam(defaultValue = "20") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = memberService.getMemberPage(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }

    // 회원 조회 페이지네이션
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberDto>> memberList(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        List<MemberDto> list = memberService.getMemberList(page, size);
        return ResponseEntity.ok(list);
    }

    // 회원 상세 조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }

    // 회원 정보 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberDto memberDto) {
        boolean isTrue = memberService.modifyMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }

    // 회원 가입
    @PostMapping("/new")
    public ResponseEntity<Boolean> memberRegiter(@RequestBody MemberDto memberDto) {
        boolean isTrue = memberService.saveMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }

    // 회원 로그인
    @GetMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto);
        boolean isTrue = memberService.login(memberDto.getEmail(), memberDto.getPwd());
        System.out.println("이메일 : " + memberDto.getEmail());
        System.out.println("패스워드 : " + memberDto.getPwd());
        return ResponseEntity.ok(isTrue);
    }

    // 회원 존재 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> isMember(@RequestParam String email) {
        log.info("email: {}", email);
        if (email != null) {
            boolean isReg = memberService.isMember(email);
            return ResponseEntity.ok(!isReg);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    // 회원 삭제
    @GetMapping("/delete/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }
}
