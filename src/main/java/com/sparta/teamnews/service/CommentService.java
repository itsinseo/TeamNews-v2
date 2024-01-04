package com.sparta.teamnews.service;

import com.sparta.teamnews.service.dto.CommentRequestDto;
import com.sparta.teamnews.service.dto.CommentResponseDto;
import com.sparta.teamnews.entity.Comment;
import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.CommentRepository;
import com.sparta.teamnews.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;

    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Post post = postService.findPost(commentRequestDto.getPostId());
        Comment comment = new Comment(commentRequestDto.getBody(), post, userDetails.getUser());

        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;
    }

    public void deleteComment(Long id, User user) {

        Comment comment = findComment(id);

        if (!comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(id);
        if (userDetails.getUsername().equals(comment.getUser().getUsername())) {
            comment.setBody(commentRequestDto.getBody());
        } else {
            throw new IllegalArgumentException("직접쓴 글이 아니면 수정할 수 없습니다.");
        }
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

    }
}
