package com.example.twitterapi.controller;

import com.example.twitterapi.dto.LikeRequest;
import com.example.twitterapi.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // POST http://localhost:8000/like
    @PostMapping("/like")
    public String like(@Valid @RequestBody LikeRequest request) {
        return likeService.like(request);
    }

    // POST http://localhost:8000/dislike
    @PostMapping("/dislike")
    public String dislike(@Valid @RequestBody LikeRequest request) {
        return likeService.dislike(request);
    }
}
