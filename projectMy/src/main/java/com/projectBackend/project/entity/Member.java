package com.projectBackend.project.entity;

import com.projectBackend.project.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "member")

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String gender;
    private int amount;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String email, String password,String name,String gender, int amount, Authority authority) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.amount = amount;
        this.authority = authority;
    }
}
