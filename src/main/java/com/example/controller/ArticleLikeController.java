package com.example.controller;

import com.example.dto.article.ArticleLikeDTO;
import com.example.enums.Reaction;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reaction")
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    public ArticleLikeController(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @PostMapping("/like&dislike")
    public ResponseEntity reaction (@Valid @RequestBody ArticleLikeDTO dto) {
        Integer profileId = SecurityUtil.getProfileId();
        articleLikeService.reaction(profileId,dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
