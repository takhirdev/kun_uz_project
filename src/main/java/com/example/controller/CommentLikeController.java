package com.example.controller;

import com.example.dto.comment.CommentLikeDTO;
import com.example.service.CommentLikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/like&dislike")
    public ResponseEntity reaction (@Valid @RequestBody CommentLikeDTO dto) {
        commentLikeService.reaction(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
