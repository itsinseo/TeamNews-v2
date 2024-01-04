package com.sparta.teamnews.service.dto;

import com.sparta.teamnews.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String profilename;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.profilename = comment.getUser().getProfilename();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
