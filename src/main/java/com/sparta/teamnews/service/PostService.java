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

    @Transactional
    public PostResponseDto updatePost(Post post, User user, PostRequestDto requestDto) {
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    public ApiResponseDto deletePost(Post post, User user) {
        postRepository.delete(post);

        return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }
}

