package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.Like;
import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.repository.LikeRepository;
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

    public LikeResponseDto createPostLike(Long postId, User user) {

        Post post = postService.findPost(postId);

        Like like = new Like(post, user);

        if (likeRepository.findByUserAndPost(user, post).isEmpty()) {
            likeRepository.save(like);
        } else {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        return new LikeResponseDto(like);
    }

    // TODO: same user check AOP
    public ApiResponseDto deletePostLike(Long likeId, User user) {

        Like like = findLike(likeId);

        if (!like.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 좋아요만 취소 가능합니다.");
        }
        likeRepository.delete(like);

        return new ApiResponseDto("삭제 성공!", 200);
    }

    public Like findLike(Long id) {
        return likeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("좋아요를 클릭한 적이 없습니다."));
    }
}
