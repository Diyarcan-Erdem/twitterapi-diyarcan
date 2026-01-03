package com.example.twitterapi.controller;

import com.example.twitterapi.dto.RetweetRequest;
import com.example.twitterapi.service.RetweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
@RequiredArgsConstructor
public class RetweetController {

    private final RetweetService retweetService;

    // POST http://localhost:8000/retweet
    @PostMapping
    public String retweet(@Valid @RequestBody RetweetRequest request) {
        return retweetService.retweet(request);
    }

    // DELETE http://localhost:8000/retweet/1
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestHeader("X-USER-ID") Long userId) {
        return retweetService.delete(id, userId);
    }
}
