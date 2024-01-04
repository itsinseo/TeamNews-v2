package com.sparta.teamnews.controller;

import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.PostService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.PostRequestDto;
import com.sparta.teamnews.service.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamnews/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.createPost(postRequestDto, user);
    }

    @Transactional
    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody PostRequestDto requestDto) {
        User user = userDetails.getUser();
        return postService.updatePost(id, user, requestDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.deletePost(id, user);
    }
}
