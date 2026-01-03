package com.example.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {

    @NotNull
    private Long tweetId;

    @NotNull
    private Long userId;

    @NotBlank
    @Size(min = 1, max = 280)
    private String content;
}
