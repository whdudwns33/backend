package com.kh.totalJpaSample.entity;
// 객체는 일회적이고 엔티티(데이터베이스)는 영속적(persistence)이다.
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
// 테이블 이름을 설정해주는 Table 어노테이션. 추가로, 대소문자 구분이 없음. 카멜표기법을 사용하지 않음.
@Table(name= "member")
@Getter @Setter @ToString
public class Member {
    // PK의 역할을 하는 Id 어노테이션
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // not null
    @Column(nullable = false)
    private String name;
    private String password;
    // unique 유일 값
    @Column(unique = true)
    private String email;
    private LocalDateTime regDate;
    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }
}
