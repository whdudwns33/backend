package com.projectBackend.project.entity;

import com.projectBackend.project.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userEmail;
    private String userPassword;
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

    @Builder
    public User(String email, String password, String nickName, String name, String addr, String tel, String gender, String BUSINESS_NUM, int point, int age, Authority authority) {
        this.userEmail = email;
        this.userPassword = password;
        this.userNickname = nickName;
        this.userAddr = addr;
        this.userPhone = tel;
        this.BUSINESS_NUM = BUSINESS_NUM;
        this.userName = name;
        this.userGen = gender;
        this.userPoint = point;
        this.authority = authority;
        this.userAge = age;
    }
}
