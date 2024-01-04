package com.sparta.teamnews.service.dto;

import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    Long postId;
    String body;

    public void setBody(String body) {
        this.body = body;
    }
}
