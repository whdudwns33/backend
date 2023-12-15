package com.projectBackend.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentMessageDTO {
    public enum MessageType {
        COMMENT,
        INIT
    }
    private String postId;
    private String commentId;
    private String commentContent;
    private String commenterEmail;
    private String authorEmail;
    private String authorIP;
}
