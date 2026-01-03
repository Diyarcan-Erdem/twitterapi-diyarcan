package com.example.twitterapi.repository;

import com.example.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    boolean existsByOriginalTweetIdAndUserId(Long originalTweetId, Long userId);

    Optional<Retweet> findByOriginalTweetIdAndUserId(Long originalTweetId, Long userId);
}
