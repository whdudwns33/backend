package com.kh.totalJpaSample.service;

import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// 해당 객체를 빈으로 등록
@Service
// 매개변수가 전부 포함된 생성자를 자동 생성.
@RequiredArgsConstructor

public class MemberService {
    // 의존성 주입.
    private final MemberRepository memberRepository;
    // 회원 등록
    public boolean saveMember(MemberDto memberDto) {
        // 등록된 email인지 중복 체크
        boolean isReg = memberRepository.existsByEmail(memberDto.getEmail());
        if (isReg) return false;

        // 회원 등록할 때마다, 새로운 회원이 필요하므로 의존성 주입 형식x
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        memberRepository.save(member);
        return true;
    }
    // 회원 전체 조회
    public List<MemberDto> getMemberList() {
            List<MemberDto> memberDtos = new ArrayList<>();
            List<Member> members = memberRepository.findAll();
            for (Member member : members) {
                memberDtos.add(convertEntityToDto(member));
            }
            return memberDtos;
        }

    // 회원 엔티티를 DTO로 변환하는 메서드 만들기.
    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setName(member.getName());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
    // 회원 상세 조회
    public MemberDto getMemberDetail(String email) {
        try {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("해당 회원의 존재하지 않습니다."));
            return convertEntityToDto(member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
