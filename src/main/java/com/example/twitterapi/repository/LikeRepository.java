package com.example.twitterapi.repository;

import com.example.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByTweetIdAndUserId(Long tweetId, Long userId);

    boolean existsByTweetIdAndUserId(Long tweetId, Long userId);
}
