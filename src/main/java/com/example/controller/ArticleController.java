package com.example.controller;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.service.ArticleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody ArticleCreateDTO dto) {
        articleService.create(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable String articleId,
                                             @Valid @RequestBody ArticleUpdateDTO dto) {
        articleService.update(articleId, dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        articleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable String id) {
        articleService.changeStatus(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getLast5ByTypeId")
    public ResponseEntity<List<ArticleDTO>> getLast5ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast5ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getLast3ByTypeId")
    public ResponseEntity<List<ArticleDTO>> getLast3ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast3ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/byId/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable String id) {
        ArticleDTO response = articleService.getByArticleId(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getLast8ByIdNotInGivenList")
    public ResponseEntity<List<ArticleDTO>> getByIdNotInGivenList(@RequestParam List<Integer> typeList) {
        List<ArticleDTO> dtoList = articleService.getByIdNotInGivenList(typeList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getLast4ByTypeIdExceptId")
    public ResponseEntity<List<ArticleDTO>> getByTypeIdExceptId(@RequestParam Integer typeId,
                                                                @RequestParam Integer articleId) {
        List<ArticleDTO> dtoList = articleService.getByTypeIdExceptId(typeId, articleId);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/filter")
    ResponseEntity<Page<ArticleDTO>> filter(@RequestBody ArticleFilterDTO dto,
                                            @RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ArticleDTO> response = articleService.filter(dto, pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);
    }
}
