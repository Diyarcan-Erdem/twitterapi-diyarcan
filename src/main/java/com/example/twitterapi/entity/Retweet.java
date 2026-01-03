package com.example.twitterapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "retweets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"original_tweet_id", "user_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Retweet edilen orijinal tweet
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "comments", "user"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_tweet_id", nullable = false)
    private Tweet originalTweet;

    // Retweet eden user
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "tweets", "comments"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
