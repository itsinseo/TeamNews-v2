package com.sparta.teamnews.controller;

import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.CommentService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teamnews/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ApiResponseDto createComment(@PathVariable Long postId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(postId, commentRequestDto, userDetails);
    }

    @PutMapping("/{commentId}")
    public ApiResponseDto updateComment(@PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        User user = userDetails.getUser();
        return commentService.updateComment(commentId, user, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return commentService.deleteComment(commentId, user);
    }
}
