package com.example.controller;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.article.ArticleUpdateDTO;
import com.example.enums.Language;
import com.example.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @PostMapping("/moderator/create")
    public ResponseEntity<HttpStatus> create(@RequestBody ArticleCreateDTO dto) {
        articleService.create(dto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable String articleId,
                                             @Valid @RequestBody ArticleUpdateDTO dto) {
        articleService.update(articleId, dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/moderator/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String articleId) {
        articleService.delete(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/publisher/changeStatus/{id}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable String articleId) {
        articleService.changeStatus(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all/last5")
    public ResponseEntity<List<ArticleDTO>> getLast5ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast5ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/last3")
    public ResponseEntity<List<ArticleDTO>> getLast3ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast3ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/all/last8")
    public ResponseEntity<List<ArticleDTO>> last8Article(@RequestBody List<String> idList) {
        List<ArticleDTO> dtoList = articleService.last8Article(idList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/byId/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable String articleId,
                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        ArticleDTO response = articleService.getById(articleId, language);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/last4ExcludingId")
    public ResponseEntity<List<ArticleDTO>> last4ExcludingId(@RequestParam Integer typeId,
                                                             @RequestParam Integer articleId) {
        List<ArticleDTO> dtoList = articleService.last4ExcludingId(typeId, articleId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/mostRead4")
    public ResponseEntity<List<ArticleDTO>> mostRead4Article() {
        List<ArticleDTO> dtoList = articleService.mostRead4Article();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/last5ByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> last5ByTypeIdAndRegionId(@RequestParam Integer typeId,
                                                                     @RequestParam Integer regionId) {
        List<ArticleDTO> dtoList = articleService.last5ByTypeIdAndRegionId(typeId, regionId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/paginationByRegion/{regionId}")
    public ResponseEntity<List<ArticleDTO>> paginationByRegion(@PathVariable Integer regionId,
                                                               @RequestParam Integer pageNumber,
                                                               @RequestParam Integer pageSize) {
        List<ArticleDTO> dtoList = articleService.paginationByRegion(regionId, pageNumber - 1, pageSize);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/last5ByCategory/{categoryId}")
    public ResponseEntity<List<ArticleDTO>> last5ByCategoryId(@PathVariable Integer categoryId) {
        List<ArticleDTO> dtoList = articleService.last5ByCategoryId(categoryId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/all/paginationByCategory/{categoryId}")
    public ResponseEntity<List<ArticleDTO>> paginationByCategory(@PathVariable Integer categoryId,
                                                                 @RequestParam Integer pageNumber,
                                                                 @RequestParam Integer pageSize) {
        List<ArticleDTO> dtoList = articleService.paginationByCategory(categoryId, pageNumber - 1, pageSize);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/viewCountIncrease/{articleId}")
    void increaseViewCount(@PathVariable String articleId) {
        articleService.increaseViewCount(articleId);
    }

    @GetMapping("/shareCountIncrease/{articleId}")
    void increaseShareCount(@PathVariable String articleId) {
        articleService.increaseShareCount(articleId);
    }

    @GetMapping("/all/filter")
    ResponseEntity<Page<ArticleDTO>> filter(@RequestBody ArticleFilterDTO dto,
                                            @RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ArticleDTO> response = articleService.filter(dto, pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);
    }
}
