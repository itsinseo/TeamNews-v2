package com.sparta.teamnews.controller;

import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.PostService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.PostRequestDto;
import com.sparta.teamnews.service.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.createPost(postRequestDto, user);
    }

    @PutMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody PostRequestDto requestDto) {
        User user = userDetails.getUser();
        Post post = postService.findPost(postId);
        return postService.updatePost(post, user, requestDto);
    }

    @DeleteMapping("/{postId}")
    public ApiResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = postService.findPost(postId);
        return postService.deletePost(post, user);
    }
}
