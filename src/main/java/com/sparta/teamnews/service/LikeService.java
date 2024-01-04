package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.Like;
import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.LikeRepository;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.dto.ApiResponseDto;
import com.sparta.teamnews.service.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    private final PostService postService;

    public LikeResponseDto createPostLike(Long postId, UserDetailsImpl userDetails) {

        Post post = postService.findPost(postId);

        Like like = new Like(post, userDetails.getUser());

        if (likeRepository.findByUserAndPost(userDetails.getUser(), post).isEmpty()) {
            likeRepository.save(like);
        } else {
            throw new IllegalArgumentException("좋아요를 누른적이 있습니다.");
        }

        return new LikeResponseDto(like);
    }


    public ApiResponseDto deletePostLike(Long likeId, User user) {

        Like like = findLike(likeId);

        if (!like.getUser().equals(user)) {
            throw new RejectedExecutionException("좋아요를 클릭한 유저가 아닙니다.");
        }
        likeRepository.delete(like);

        return new ApiResponseDto("삭제 성공!", 200);
    }

    public Like findLike(Long id) {
        return likeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("좋아요를 클릭한 적이 없습니다."));
    }
}
