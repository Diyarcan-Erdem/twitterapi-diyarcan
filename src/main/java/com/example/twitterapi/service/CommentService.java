package com.example.twitterapi.service;

import com.example.twitterapi.dto.CommentCreateRequest;
import com.example.twitterapi.dto.CommentUpdateRequest;
import com.example.twitterapi.entity.Comment;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.entity.User;
import com.example.twitterapi.exception.NotFoundException;
import com.example.twitterapi.exception.UnauthorizedException;
import com.example.twitterapi.repository.CommentRepository;
import com.example.twitterapi.repository.TweetRepository;
import com.example.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public Comment create(CommentCreateRequest request) {
        Tweet tweet = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .tweet(tweet)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public Comment update(Long commentId, Long requesterUserId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if (!comment.getUser().getId().equals(requesterUserId)) {
            throw new UnauthorizedException("Only comment owner can update this comment");
        }

        comment.setContent(request.getContent());
        return commentRepository.save(comment);
    }

    public void delete(Long commentId, Long requesterUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        Long tweetOwnerId = comment.getTweet().getUser().getId();
        Long commentOwnerId = comment.getUser().getId();

        if (!requesterUserId.equals(tweetOwnerId) && !requesterUserId.equals(commentOwnerId)) {
            throw new UnauthorizedException("Only tweet owner or comment owner can delete this comment");
        }

        commentRepository.delete(comment);
    }
}
