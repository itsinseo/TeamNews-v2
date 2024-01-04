package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.Comment;
import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.CommentRepository;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public ApiResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postService.findPost(postId);
        Comment comment = new Comment(commentRequestDto.getBody(), post, userDetails.getUser());

        commentRepository.save(comment);

        return new ApiResponseDto("댓글 작성 완료", HttpStatus.OK.value());
    }

    @Transactional
    public ApiResponseDto updateComment(Comment comment, User user, CommentRequestDto commentRequestDto) {
        comment.setBody(commentRequestDto.getBody());

        return new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value());
    }

    public ApiResponseDto deleteComment(Comment comment, User user) {
        commentRepository.delete(comment);

        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));
    }
}
