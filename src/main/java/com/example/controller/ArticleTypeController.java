package com.example.controller;

import com.example.dto.article.ArticleTypesCreateDTO;
import com.example.dto.article.ArticleTypesDTO;
import com.example.enums.Language;
import com.example.service.ArticleTypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/article_types")
public class ArticleTypeController {
    @Autowired
    private ArticleTypesService articleTypesService;

    @PostMapping(value = "/create")
    public ResponseEntity<ArticleTypesDTO> create(@Valid @RequestBody ArticleTypesCreateDTO dto) {
        ArticleTypesDTO response = articleTypesService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleTypesDTO>> getAll() {
        return ResponseEntity.ok(articleTypesService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleTypesDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<ArticleTypesDTO> dtoList = articleTypesService.getAllByLang(lang);
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleTypesDTO> updateRegion(@PathVariable Integer id,
                                                     @Valid @RequestBody ArticleTypesCreateDTO dto) {
        ArticleTypesDTO response = articleTypesService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean result = articleTypesService.delete(id);
        return ResponseEntity.ok(result);
    }
}
