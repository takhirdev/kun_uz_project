package com.example.controller;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("moderator/create")
    public ResponseEntity<HttpStatus> create( @RequestBody ArticleCreateDTO dto,
                                              @RequestHeader("Authorization") String token) {
        articleService.create(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable String id,
                                             @Valid @RequestBody ArticleUpdateDTO dto,
                                             @RequestHeader("Authorization") String token) {
        articleService.update(id, dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/moderator/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id,
                                             @RequestHeader("Authorization") String token) {
        articleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/publisher/changeStatus/{id}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable String id,
                                                   @RequestHeader("Authorization") String token){
        articleService.changeStatus(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getLast5")
    public ResponseEntity<List<ArticleDTO>> getLast5(@RequestParam Integer typeID){
        List<ArticleDTO> dtoList = articleService.getLast5(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/getLast3")
    public ResponseEntity<List<ArticleDTO>> getLast3(@RequestParam Integer typeID){
        List<ArticleDTO> dtoList = articleService.getLast3(typeID);
        return ResponseEntity.ok(dtoList);
    }
}
