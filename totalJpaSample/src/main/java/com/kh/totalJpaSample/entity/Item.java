package com.kh.totalJpaSample.entity;
import com.kh.totalJpaSample.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity // 클래스를 엔티티로 선언
@Table(name = "item")
public class Item {
    @Id // PK 지정
    @Column(name = "item_id")   // id의 이름이 item_id로 만들어짐
    @GeneratedValue(strategy = GenerationType.AUTO) // JPA가 자동으로 생성 전략 결정
    private Long id;    // 상품 코드
    @Column(nullable = false, length = 50 ) // not null, 글자 수 50자
    private String itemName; // 상품명
    @Column(name = "price", nullable = false)
    private int price;
    @Column(nullable = false)
    private int stockNumber;    // 재고 수량
    @Lob
    @Column(nullable = false)
    private String itemDetail;     //상품 상세 설명
    @Enumerated(EnumType.STRING)    //enum타입으로 정의된 값을 문자열로 DB에 저장
    private ItemSellStatus itemSellStatus;  // enum 타입의 객체. 인스턴스는 SELL, SOLD_OUT
    private LocalDateTime regTime; // 등록시간
    private LocalDateTime updateTime;   // 수정 시간

}
