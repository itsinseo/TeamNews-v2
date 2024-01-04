package com.sparta.teamnews.controller;

import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.CommentService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teamnews/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(commentRequestDto, userDetails);
        return new ApiResponseDto("댓글 작성 완료", HttpStatus.OK.value());
    }

    @PutMapping("/{id}")            //댓글 수정 수정
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(id, commentRequestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());
    }
}
