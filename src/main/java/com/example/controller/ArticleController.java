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
    public ResponseEntity<HttpStatus> delete(@PathVariable String articleId) {
        articleService.delete(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable String articleId) {
        articleService.changeStatus(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/last5")
    public ResponseEntity<List<ArticleDTO>> getLast5ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast5ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/last3")
    public ResponseEntity<List<ArticleDTO>> getLast3ByTypeId(@RequestParam Integer typeID) {
        List<ArticleDTO> dtoList = articleService.getLast3ByTypeId(typeID);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/last8")
    public ResponseEntity<List<ArticleDTO>> last8Article(@RequestBody List<String> idList) {
        List<ArticleDTO> dtoList = articleService.last8Article(idList);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/byId/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable String articleId,
                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language) {
        ArticleDTO response = articleService.getById(articleId, language);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/last4ExcludingId")
    public ResponseEntity<List<ArticleDTO>> last4ExcludingId(@RequestParam Integer typeId,
                                                             @RequestParam Integer articleId) {
        List<ArticleDTO> dtoList = articleService.last4ExcludingId(typeId, articleId);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/mostRead4")
    public ResponseEntity<List<ArticleDTO>> mostRead4Article() {
        List<ArticleDTO> dtoList = articleService.mostRead4Article();
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/last5ByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> last5ByTypeIdAndRegionId(@RequestParam Integer typeId,
                                                                     @RequestParam Integer regionId) {
        List<ArticleDTO> dtoList = articleService.last5ByTypeIdAndRegionId(typeId, regionId);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/paginationByRegion/{regionId}")
    public ResponseEntity<List<ArticleDTO>> paginationByRegion(@PathVariable Integer regionId,
                                                               @RequestParam Integer pageNumber,
                                                               @RequestParam Integer pageSize) {
        List<ArticleDTO> dtoList = articleService.paginationByRegion(regionId, pageNumber - 1, pageSize);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/last5ByCategory/{categoryId}")
    public ResponseEntity<List<ArticleDTO>> last5ByCategoryId(@PathVariable Integer categoryId) {
        List<ArticleDTO> dtoList = articleService.last5ByCategoryId(categoryId);
        return ResponseEntity.ok(dtoList);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/paginationByCategory/{categoryId}")
    public ResponseEntity<List<ArticleDTO>> paginationByCategory(@PathVariable Integer categoryId,
                                                                 @RequestParam Integer pageNumber,
                                                                 @RequestParam Integer pageSize) {
        List<ArticleDTO> dtoList = articleService.paginationByCategory(categoryId, pageNumber - 1, pageSize);
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
