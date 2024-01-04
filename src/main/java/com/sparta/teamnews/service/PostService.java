package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.PostRepository;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.PostRequestDto;
import com.sparta.teamnews.service.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {

        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    // TODO: File uploading feature implementation
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .user(user)
                .build();

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // TODO: user checking to AOP
    @Transactional
    public PostResponseDto updatePost(Long id, User user, PostRequestDto requestDto) {

        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 게시글만 수정 가능합니다.");
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    // TODO: user checking to AOP
    public ApiResponseDto deletePost(Long id, User user) {

        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 게시글만 삭제 가능합니다.");
        }

        postRepository.delete(post);

        return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }
}

