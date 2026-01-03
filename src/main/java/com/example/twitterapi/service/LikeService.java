package com.example.twitterapi.service;

import com.example.twitterapi.dto.LikeRequest;
import com.example.twitterapi.entity.Like;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.entity.User;
import com.example.twitterapi.exception.NotFoundException;
import com.example.twitterapi.repository.LikeRepository;
import com.example.twitterapi.repository.TweetRepository;
import com.example.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public String like(LikeRequest request) {
        // tweet var mı?
        Tweet tweet = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        // user var mı?
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // aynı like zaten var mı?
        if (likeRepository.existsByTweetIdAndUserId(tweet.getId(), user.getId())) {
            return "Already liked";
        }

        Like like = Like.builder()
                .tweet(tweet)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
        return "Liked";
    }

    public String dislike(LikeRequest request) {
        Like like = likeRepository.findByTweetIdAndUserId(request.getTweetId(), request.getUserId())
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(like);
        return "Disliked";
    }
}
