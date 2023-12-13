package com.projectBackend.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "performance")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Performance {
    @Id
    @Column(name = "performance_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long performanceId;

    private String performanceName;

    @OneToMany(mappedBy = "performance", orphanRemoval = true)
    private List<Performer> performers; // 여러 명의 참여자를 담을 목록

    private String venue;
    private String detailVenue;
    private String performanceDate;
    private int price;
    private String description;
    private int seatCount;
    private String performanceImage;
}

