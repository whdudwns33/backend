package com.projectBackend.project.dto;

import com.projectBackend.project.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String name;
    private String email;
    private String gender;
    private int amount;
    private String image;
    private LocalDateTime regDate;

    // Member -> MemberResDto
    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
