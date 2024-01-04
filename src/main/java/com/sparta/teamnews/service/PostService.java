package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.PostRepository;
import com.sparta.teamnews.service.dto.PostRequestDto;
import com.sparta.teamnews.service.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${upload.path}")
    private static String uploadPath;

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

    //게시물 생성
    public void createPost(String title, String content, User user, MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return;
        }

        // 원래 파일 이름 추출
        String originalFileName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = uploadPath + savedName;

        files.transferTo(new File(savedPath));

        Post post = new Post(title, content, originalFileName, savedName, savedPath, user);

        postRepository.save(post);
    }

    @Transactional
    public PostResponseDto updatePost(User user, Long id, PostRequestDto requestDto) {

        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, User user) {

        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }
        postRepository.delete(post);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }
}

