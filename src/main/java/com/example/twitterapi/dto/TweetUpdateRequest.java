package com.example.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetUpdateRequest {
    @NotBlank
    private String content;
}
