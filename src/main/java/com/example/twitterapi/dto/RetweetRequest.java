package com.example.twitterapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetweetRequest {

    @NotNull
    private Long tweetId; // original tweet id

    @NotNull
    private Long userId;  // retweet yapan
}
