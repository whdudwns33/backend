package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Setter
@Getter
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cartName;

    @OneToOne   // 회원 엔티티와 1대1 맵핑을 하기 위함
    @JoinColumn(name = "member_id")
    // 참조하는 엔티티의 객체를 조인해야함.
    private Member member;




}
