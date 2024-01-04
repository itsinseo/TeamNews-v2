package com.sparta.teamnews.service.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    Long postId;
    String body;

    public void setBody(String body) {
        this.body = body;
    }
}
