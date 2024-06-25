package com.example.controller;

import com.example.dto.article.SavedArticleDTO;
import com.example.service.SavedArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved_article ")
public class SavedArticleController {
    private final SavedArticleService savedArticleService;

    public SavedArticleController(SavedArticleService savedArticleService) {
        this.savedArticleService = savedArticleService;
    }

    @PostMapping("/save/{articleId}")
    public ResponseEntity save (@PathVariable String articleId) {
        Boolean response = savedArticleService.save(articleId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity delete(@PathVariable String articleId) {
        savedArticleService.delete(articleId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/current/getAll")
    public ResponseEntity getAll() {
        List<SavedArticleDTO> response = savedArticleService.getAll();
        return ResponseEntity.ok(response);
    }
}
