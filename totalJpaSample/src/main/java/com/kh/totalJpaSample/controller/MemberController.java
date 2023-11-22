package com.kh.totalJpaSample.controller;

import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.kh.totalJpaSample.utils.Common.CROSS_ORIGIN;

// log for 4j
// 로그 기록을 남기는 어노테이션
@Slf4j
@CrossOrigin(origins = CROSS_ORIGIN )
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 회원 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> memberRegister(@RequestBody MemberDto memberDto) {
        boolean isTrue = memberService.saveMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }


    // 회원 상세 조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }

    // 페이지 네이션 조회
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberDto>> memberList (@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        List<MemberDto> list = memberService.getMemberList(page, size);
        return ResponseEntity.ok(list);
    }

    // 총 페이지 수 조회
    @GetMapping("/list/page-cnt")
    public ResponseEntity<Integer> memberPageCount(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        int pageCnt = memberService.getMemberPage(page, size);
        return ResponseEntity.ok(pageCnt);
    }
}
