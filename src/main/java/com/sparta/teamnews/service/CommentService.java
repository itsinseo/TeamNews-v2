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

import java.util.concurrent.RejectedExecutionException;

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

    // TODO: same user AOP
    @Transactional
    public ApiResponseDto updateComment(Long id, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(id);

        if (!user.equals(comment.getUser())) {
            throw new RejectedExecutionException("직접쓴 글이 아니면 수정할 수 없습니다.");
        }

        comment.setBody(commentRequestDto.getBody());

        return new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value());
    }

    // TODO: same user AOP
    public ApiResponseDto deleteComment(Long id, User user) {

        Comment comment = findComment(id);

        if (!comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);

        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

    }
}
