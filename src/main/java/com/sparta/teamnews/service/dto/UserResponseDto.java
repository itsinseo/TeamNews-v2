package com.sparta.teamnews.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.teamnews.entity.User;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private String username;
    private String introduction;
    private String profileName;
    private String msg;
    private Integer statusCode;

    // 프로필 수정 Response 용 생성자
    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.profileName = user.getProfileName();
        this.introduction = user.getIntroduction();
    }

    // 로그인 Response 용 생성자
    public UserResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
