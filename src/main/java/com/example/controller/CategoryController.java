package com.example.controller;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDTO> create(@RequestHeader("Authorization") String token,
                                              @Valid @RequestBody CategoryCreateDTO dto) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAll(@RequestHeader("Authorization") String token) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<CategoryDTO> dtoList = categoryService.getAllByLang(lang);
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateRegion(@RequestHeader("Authorization") String token,
                                                    @PathVariable Integer id,
                                                    @Valid @RequestBody CategoryCreateDTO dto) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        CategoryDTO response = categoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader("Authorization") String token,
                                          @PathVariable Integer id) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        Boolean result = categoryService.delete(id);
        return ResponseEntity.ok(result);
    }
}
