package com.projectBackend.project.dto;

import com.projectBackend.project.constant.Authority;
import com.projectBackend.project.entity.Member;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResDto {
    private String userEmail;
    private String userPasswword;
    private String userNickname;
    private String userName;
    private String userAddr;
    private String userPhone;
    private String userGen;
    private int userAge;
    private int userPoint;
    private String BUSINESS_NUM;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // Member -> MemberResDto
    public static UserResDto of(Member member) {
        // 응답에는 비밀번호가 필요없기에 일다 제외
        return UserResDto.builder()
                .userEmail(member.getUserEmail())
                .userNickname(member.getUserNickname())
                .userName(member.getUserName())
                .userAddr(member.getUserAddr())
                .userPhone(member.getUserPhone())
                .userGen(member.getUserGen())
                .userAge(member.getUserAge())
                .userPoint(member.getUserPoint())
                .BUSINESS_NUM(member.getBUSINESS_NUM())
                .authority(member.getAuthority())
                .build();
    }
}
