package com.sparta.teamnews.controller;

import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.PostService;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.PostRequestDto;
import com.sparta.teamnews.service.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void createPost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("file") MultipartFile file,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        postService.createPost(title, content, userDetails.getUser(), file);
    }

    @Transactional
    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(userDetails.getUser(), id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
    }
}
