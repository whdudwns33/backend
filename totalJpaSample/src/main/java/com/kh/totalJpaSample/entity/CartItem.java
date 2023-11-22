package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    // 다대 일 관계
    // 하나의 장바구니에는 여러개의 상품을 담을 수 있음.
    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;
    // 다대 일 관계
    // 하나의 아이템은 여러 장바구니에 상품이 담길 수 있음
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
}
