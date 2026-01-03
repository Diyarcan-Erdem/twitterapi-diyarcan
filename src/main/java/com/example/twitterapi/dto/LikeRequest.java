package com.example.twitterapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequest {

    @NotNull
    private Long tweetId;

    @NotNull
    private Long userId;
}
