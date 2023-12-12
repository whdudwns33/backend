package com.projectBackend.project.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "MUSIC")
public class Music {

    @Id
    @Column(name = "MUSIC_ID")
    private String musicId;
    private String musicTitle;
    private String artistName;
    private String lyricist;
    private String composer;
    private String genre;
    private int purchaseCount;
    private String lyrics;
    private Date releaseDate;
    private String thumbnailImg;
    private String promoImg;

    @ManyToOne
    @JoinColumn(name = "USER_EMAIL", nullable = false)
    private Member user;

}
