package com.sparta.teamnews.repository;

import com.sparta.teamnews.entity.Like;
import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
