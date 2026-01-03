package com.example.twitterapi.service;

import com.example.twitterapi.dto.TweetCreateRequest;
import com.example.twitterapi.dto.TweetUpdateRequest;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.entity.User;
import com.example.twitterapi.exception.NotFoundException;
import com.example.twitterapi.exception.UnauthorizedException;
import com.example.twitterapi.repository.TweetRepository;
import com.example.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public Tweet create(TweetCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Tweet tweet = Tweet.builder()
                .content(request.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return tweetRepository.save(tweet);
    }

    public List<Tweet> findByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    public Tweet findById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));
    }

    public Tweet update(Long id, TweetUpdateRequest request) {
        Tweet tweet = findById(id);
        tweet.setContent(request.getContent());
        return tweetRepository.save(tweet);
    }

    // ✅ DELETE: Sadece tweet sahibi silebilir
    public void delete(Long tweetId, Long requesterUserId) {
        Tweet tweet = findById(tweetId);

        if (!tweet.getUser().getId().equals(requesterUserId)) {
            throw new UnauthorizedException("Only tweet owner can delete this tweet");
        }

        tweetRepository.delete(tweet);
    }
}
