package com.example.kh.testProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;
    private String title;
    private String content;
    private String imgPath;
    private LocalDateTime regDate;
    // regTime을 저장해주는 메서드
    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }

    // 조인 관계 지정
    @ManyToOne(fetch = FetchType.LAZY) // 지연 전략
    @JoinColumn(name = "member_id") // 외래키
    private Member member; // 작성자

}
