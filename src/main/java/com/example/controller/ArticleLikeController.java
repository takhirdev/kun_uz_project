package com.example.controller;

import com.example.enums.Reaction;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
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

    @PostMapping("/like&dislike/{articleId}")
    public ResponseEntity reaction (@PathVariable String articleId,
                                    @RequestParam Reaction reaction) {
        Integer profileId = SecurityUtil.getProfileId();
        articleLikeService.reaction(articleId, profileId,reaction);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
