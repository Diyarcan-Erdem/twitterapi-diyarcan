package com.example.twitterapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 280)
    @Column(nullable = false, length = 280)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Tweet -> Comment ilişkisi
    // JSON döngüsü + Hibernate proxy sorunlarını engeller
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "comments", "user"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;

    // User -> Comment ilişkisi
    // password zaten gizli; ayrıca olası geri listeleri kapat
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "tweets", "comments"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
