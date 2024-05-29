package com.example.controller;

import com.example.dto.articleType.ArticleTypesCreateDTO;
import com.example.dto.articleType.ArticleTypesDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypesService;
import com.example.util.SecurityUtil;
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
    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(value = "/create")
    public ResponseEntity<ArticleTypesDTO> create(@RequestHeader("Authorization") String token,
                                                  @Valid @RequestBody ArticleTypesCreateDTO dto) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        ArticleTypesDTO response = articleTypesService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleTypesDTO>> getAll(@RequestHeader("Authorization") String token) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypesService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleTypesDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<ArticleTypesDTO> dtoList = articleTypesService.getAllByLang(lang);
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleTypesDTO> updateRegion(@RequestHeader("Authorization") String token,
                                                        @PathVariable Integer id,
                                                        @Valid @RequestBody ArticleTypesCreateDTO dto) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        ArticleTypesDTO response = articleTypesService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader("Authorization") String token,
                                          @PathVariable Integer id) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        Boolean result = articleTypesService.delete(id);
        return ResponseEntity.ok(result);
    }
}
