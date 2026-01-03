package com.example.twitterapi.controller;

import com.example.twitterapi.dto.TweetCreateRequest;
import com.example.twitterapi.dto.TweetUpdateRequest;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    // POST http://localhost:8000/tweet
    @PostMapping
    public Tweet create(@Valid @RequestBody TweetCreateRequest request) {
        return tweetService.create(request);
    }

    // GET http://localhost:8000/tweet/findByUserId?userId=1
    @GetMapping("/findByUserId")
    public List<Tweet> findByUserId(@RequestParam Long userId) {
        return tweetService.findByUserId(userId);
    }

    // GET http://localhost:8000/tweet/findById?id=1
    @GetMapping("/findById")
    public Tweet findById(@RequestParam Long id) {
        return tweetService.findById(id);
    }

    // PUT http://localhost:8000/tweet/1
    @PutMapping("/{id}")
    public Tweet update(@PathVariable Long id, @Valid @RequestBody TweetUpdateRequest request) {
        return tweetService.update(id, request);
    }

    // DELETE http://localhost:8000/tweet/1
    // Header: X-USER-ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestHeader("X-USER-ID") Long userId) {
        tweetService.delete(id, userId);
        return "Tweet deleted";
    }
}
