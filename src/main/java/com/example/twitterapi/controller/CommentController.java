package com.example.twitterapi.controller;

import com.example.twitterapi.dto.CommentCreateRequest;
import com.example.twitterapi.dto.CommentUpdateRequest;
import com.example.twitterapi.entity.Comment;
import com.example.twitterapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // POST http://localhost:8000/comment
    @PostMapping
    public Comment create(@Valid @RequestBody CommentCreateRequest request) {
        return commentService.create(request);
    }

    // PUT http://localhost:8000/comment/1   (Header: X-USER-ID)
    @PutMapping("/{id}")
    public Comment update(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        return commentService.update(id, userId, request);
    }

    // DELETE http://localhost:8000/comment/1 (Header: X-USER-ID)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestHeader("X-USER-ID") Long userId) {
        commentService.delete(id, userId);
        return "Comment deleted";
    }
}
