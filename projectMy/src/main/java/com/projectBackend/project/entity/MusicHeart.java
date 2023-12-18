package com.projectBackend.project.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "music_like")
public class MusicHeart {

    @Id
    @Column(name = "musicheart_id")
    private String musicHeartId;

    @Column(name = "heart_cnt")
    private int heartCount;

    @Column(name = "heart_date")
    private LocalDateTime heartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;



}