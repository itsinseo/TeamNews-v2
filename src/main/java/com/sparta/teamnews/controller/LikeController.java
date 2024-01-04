package com.sparta.teamnews.controller;

import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.LikeService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teamnews/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public LikeResponseDto createPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return likeService.createPostLike(postId, user);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<ApiResponseDto> deletePostLike(@PathVariable Long likeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.deletePostLike(likeId, user));
    }
}
