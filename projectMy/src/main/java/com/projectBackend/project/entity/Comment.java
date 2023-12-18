package com.projectBackend.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "comment")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private  Community community;

    private LocalDateTime regDate;
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @JsonIgnore
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> childComments = new ArrayList<>();

    private String email;
    private String nickName;
    private String password;

}
