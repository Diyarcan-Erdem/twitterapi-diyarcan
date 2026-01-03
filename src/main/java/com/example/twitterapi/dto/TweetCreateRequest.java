package com.example.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetCreateRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}
