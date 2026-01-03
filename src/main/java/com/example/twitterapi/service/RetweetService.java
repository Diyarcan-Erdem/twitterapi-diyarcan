package com.example.twitterapi.service;

import com.example.twitterapi.dto.RetweetRequest;
import com.example.twitterapi.entity.Retweet;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.entity.User;
import com.example.twitterapi.exception.NotFoundException;
import com.example.twitterapi.exception.UnauthorizedException;
import com.example.twitterapi.repository.RetweetRepository;
import com.example.twitterapi.repository.TweetRepository;
import com.example.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public String retweet(RetweetRequest request) {
        Tweet original = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (retweetRepository.existsByOriginalTweetIdAndUserId(original.getId(), user.getId())) {
            return "Already retweeted";
        }

        Retweet retweet = Retweet.builder()
                .originalTweet(original)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        retweetRepository.save(retweet);
        return "Retweeted";
    }

    // Delete: sadece retweet sahibi silebilsin
    public String delete(Long retweetId, Long requesterUserId) {
        Retweet retweet = retweetRepository.findById(retweetId)
                .orElseThrow(() -> new NotFoundException("Retweet not found"));

        if (!retweet.getUser().getId().equals(requesterUserId)) {
            throw new UnauthorizedException("Only retweet owner can delete this retweet");
        }

        retweetRepository.delete(retweet);
        return "Retweet deleted";
    }
}
