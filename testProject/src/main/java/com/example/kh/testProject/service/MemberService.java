package com.example.kh.testProject.service;

import com.example.kh.testProject.dto.MemberDto;
import com.example.kh.testProject.entity.Member;
import com.example.kh.testProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입 여부 체크
    public boolean isMember(String email) {
        if (email != null) {
            return memberRepository.existsByEmail(email);
        }
        else {
            return false;
        }
    }
    // 회원 상세 조회
    public MemberDto getMemberDetail(String email) {
        // 레지토리에서 해당 이메일에 해당하는 멤버의 정보 출력
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        return convertEntityToDto(member);
    }

    // 회원 가입: 가입 되면 true 아니면 false
    public boolean saveMember (MemberDto memberDto) {
        try {
            Member member = new Member();
            member.setEmail(memberDto.getEmail());
            member.setName(memberDto.getName());
            member.setPassword(memberDto.getPwd());
            member.setImage(memberDto.getImage());
            member.setRegDate(memberDto.getRegDate());
            memberRepository.save(member);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("회원가입 오류");
            return false;
        }
    }

    // 회원 수정
    public boolean modifyMember(MemberDto memberDto) {
        try {
            // 해당 DTO의 이메일을 통해 레지토리로 엔티티 내에서 존재하는지 확인.
            Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            member.setName(memberDto.getName());
            member.setImage(memberDto.getImage());
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인
    public boolean login(String email, String pwd) {
        if (email != null && pwd != null) {
        Optional<Member> member = memberRepository.findByEmailAndPassword(email, pwd);
        return member.isPresent();
        } else {
            return false;
        }
    }

    // 회원 삭제
    public boolean deleteMember(String email) {
        try {
            // 멤버 엔티티에서 email에 해당하는 객체가 존재하는지 확인 후 
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            // 해당 객체가 있으면 삭제
            memberRepository.delete(member);
            return true; // 회원이 존재하면 true 반환
        } catch (RuntimeException e) {
            return false; // 회원이 존재하지 않으면 false 반환
        }
    }

    //회원 전체 조회
    //데이터베이스에서 백엔드를 통해 프론트엔드로 전달하기 위한 DTO사용
    public List<MemberDto> getMemberList() {
        // 멤버 엔티티에서 모든 멤버 객체를 DTO리스트에서 저장. 
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member member : members) {
            memberDtoList.add(convertEntityToDto(member));
        }
        return memberDtoList;
    }
    // 총 페이지 수
    public int getMemberPage(Pageable pageable) {
        return memberRepository.findAll(pageable).getTotalPages();
    }

    // 페이지 네이션
    public List<MemberDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Member> members = memberRepository.findAll(pageable).getContent();
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }





    private MemberDto convertEntityToDto(Member member) {
        // 엔터티에 있는 객체 정보를 DTO로 저장. 즉, 백엔드에서 프론트엔드로 정보를 전달하기 위함.
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setPwd(member.getPassword());
        memberDto.setImage(member.getImage());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}
