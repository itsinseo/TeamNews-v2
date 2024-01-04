package com.sparta.teamnews.service.dto;

import com.sparta.teamnews.entity.Comment;
import com.sparta.teamnews.entity.Like;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class LikeResponseDto {

    private Long id;
    private Long postId;
    private Long userId;

    public LikeResponseDto(Like like) {
        this.id = like.getId();
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();

    }
}
