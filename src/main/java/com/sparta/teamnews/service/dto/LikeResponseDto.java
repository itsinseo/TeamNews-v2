package com.sparta.teamnews.service.dto;

import com.sparta.teamnews.entity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDto {

    private Long likeId;
    private Long postId;
    private Long userId;

    public LikeResponseDto(Like like) {
        this.likeId = like.getLikeId();
        this.postId = like.getPost().getPostId();
        this.userId = like.getUser().getUserId();
    }
}
